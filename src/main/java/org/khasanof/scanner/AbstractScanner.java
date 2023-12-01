package org.khasanof.scanner;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author Nurislom
 * @see org.khasanof.scanner
 * @since 11/28/2023 7:05 PM
 */
public abstract class AbstractScanner {

    private static final List<String> beanScopes = List.of(BeanDefinition.SCOPE_PROTOTYPE, BeanDefinition.SCOPE_SINGLETON);

    public static boolean isProxyClass(Object bean) {
        return ((AopUtils.isAopProxy(bean) || AopUtils.isCglibProxy(bean)) && bean instanceof Advised)
                || bean.getClass().getName().contains("$$");
    }

    public static boolean isNotAcceptedScope(String scope) {
        return !beanScopes.contains(scope);
    }

    public static BeanDefinitionRegistry getBeanDefinitionRegistry(ApplicationContext applicationContext) {
        return (BeanDefinitionRegistry) applicationContext;
    }

}
