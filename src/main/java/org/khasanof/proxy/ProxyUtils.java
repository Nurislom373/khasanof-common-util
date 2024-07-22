package org.khasanof.proxy;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 * @author Nurislom
 * @see org.khasanof.proxy
 * @since 4/24/2024 6:16 PM
 */
public abstract class ProxyUtils {

    /**
     *
     * @param bean
     * @return
     */
    public static boolean isProxy(Object bean) {
        return AopUtils.isAopProxy(bean) && bean instanceof Advised;
    }

    /**
     *
     * @param proxyBean
     * @return
     */
    public static Object unwrapProxyBean(Object proxyBean) {
        try {
            if (AopUtils.isAopProxy(proxyBean) && proxyBean instanceof Advised) {
                return ((Advised) proxyBean).getTargetSource().getTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxyBean;
    }
}
