package org.khasanof.loader;

import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * @author Nurislom
 * @see org.khasanof.loader
 * @since 12/2/2023 12:36 AM
 */
public abstract class KhasanofLoaderUtils {

    public static Class<?> springClassLoader(String name, ApplicationContext applicationContext) {
        try {
            ClassLoader contextClassLoader = applicationContext.getClassLoader();
            if (Objects.nonNull(contextClassLoader)) {
                return contextClassLoader.loadClass(name);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> classLoader(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
