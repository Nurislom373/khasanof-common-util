package org.khasanof.scanner.recursive;

import org.khasanof.scanner.BeanAnnotatedField;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveTask;

import static org.khasanof.loader.KhasanofLoaderUtils.classLoader;
import static org.khasanof.scanner.AbstractScanner.*;

/**
 * @author Nurislom
 * @see org.khasanof.scanner.recursive
 * @since 11/28/2023 7:03 PM
 */
public class BeanScannerTask extends RecursiveTask<Map<BeanDefinition, BeanAnnotatedField>> {

    private static final int THRESHOLD = 500; // Adjust the threshold as needed
    private final String[] beanDefinitionNames;
    private final int start;
    private final int end;
    private final ApplicationContext applicationContext;
    private final Class<? extends Annotation> annotationType;

    public BeanScannerTask(String[] beanDefinitionNames, int start, int end, ApplicationContext applicationContext, Class<? extends Annotation> annotationType) {
        this.beanDefinitionNames = beanDefinitionNames;
        this.start = start;
        this.end = end;
        this.applicationContext = applicationContext;
        this.annotationType = annotationType;
    }

    @Override
    protected Map<BeanDefinition, BeanAnnotatedField> compute() {
        if (end - start <= THRESHOLD) {
            return scanRange(start, end);
        } else {
            int mid = (start + end) >>> 1;
            BeanScannerTask leftTask = new BeanScannerTask(beanDefinitionNames, start, mid, applicationContext, annotationType);
            BeanScannerTask rightTask = new BeanScannerTask(beanDefinitionNames, mid, end, applicationContext, annotationType);

            leftTask.fork();
            Map<BeanDefinition, BeanAnnotatedField> rightResult = rightTask.compute();
            Map<BeanDefinition, BeanAnnotatedField> leftResult = leftTask.join();

            leftResult.putAll(rightResult);
            return leftResult;
        }
    }

    private Map<BeanDefinition, BeanAnnotatedField> scanRange(int start, int end) {
        Map<BeanDefinition, BeanAnnotatedField> result = new ConcurrentHashMap<>();
        BeanDefinitionRegistry beanDefinitionRegistry = getBeanDefinitionRegistry(applicationContext);

        for (int i = start; i < end; i++) {
            String beanName = beanDefinitionNames[i];
            if (applicationContext.containsBean(beanName) && applicationContext.isSingleton(beanName)) {
                BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanName);
                if (Objects.nonNull(beanDefinition.getScope()) && isNotAcceptedScope(beanDefinition.getScope())) {
                    continue;
                }
                Object bean = applicationContext.getBean(beanName);

                Result validResult = getValidResult(bean, beanDefinition);

                if (validResult.isValidBean) {
                    checkValidBean(bean, beanName, beanDefinition, validResult, result);
                }
            }
        }

        return result;
    }

    private void checkValidBean(Object bean, String beanName, BeanDefinition beanDefinition, Result validResult, Map<BeanDefinition, BeanAnnotatedField> result) {
        BeanAnnotatedField annotatedField = new BeanAnnotatedField()
                .instance(bean)
                .beanName(beanName)
                .beanDefinition(beanDefinition)
                .fields(new CopyOnWriteArrayList<>());

        if (isAnnotatedFieldFound(validResult, annotatedField)) {
            result.put(beanDefinition, annotatedField);
        }
    }

    private boolean isAnnotatedFieldFound(Result validResult, BeanAnnotatedField annotatedField) {
        boolean annotatedFieldFound = false;
        if (Objects.nonNull(validResult.beanClass)) {
            // Scan fields of the bean class
            Field[] fields = validResult.beanClass.getDeclaredFields();
            if (fields.length >= 1) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(annotationType)) {
                        annotatedFieldFound = true;
                        annotatedField.getFields().add(field);
                    }
                }
            }
        }
        return annotatedFieldFound;
    }

    private static Result getValidResult(Object bean, BeanDefinition beanDefinition) {
        Class<?> beanClass = null;
        boolean isValidBean = true;
        if (isProxyClass(bean)) {
            if (bean instanceof Advised) {
                beanClass = ((Advised) bean).getTargetSource().getTargetClass();
            } else {
                isValidBean = false;
            }
        } else {
            beanClass = classLoader(beanDefinition.getBeanClassName());
        }
        return new Result(beanClass, isValidBean);
    }

    private static class Result {
        public final Class<?> beanClass;
        public final boolean isValidBean;

        public Result(Class<?> beanClass, boolean isValidBean) {
            this.beanClass = beanClass;
            this.isValidBean = isValidBean;
        }
    }
}
