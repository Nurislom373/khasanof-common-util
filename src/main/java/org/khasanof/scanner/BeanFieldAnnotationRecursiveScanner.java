package org.khasanof.scanner;

import org.khasanof.scanner.recursive.BeanScannerTask;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Nurislom
 * @see org.khasanof.scanner
 * @since 11/28/2023 7:03 PM
 */
public class BeanFieldAnnotationRecursiveScanner {

    /**
     * This method is used to retrieve the any annotation beans set to the field.
     *
     * @param applicationContext - beanFactory
     * @param annotationType - annotation to search
     * @return bean definition and bean instance
     */
    public static Map<BeanDefinition, BeanAnnotatedField> findBeansWithFieldAnnotation(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        BeanScannerTask task = new BeanScannerTask(beanDefinitionNames, 0, beanDefinitionNames.length, applicationContext, annotationType);
        return forkJoinPool.invoke(task);
    }

}
