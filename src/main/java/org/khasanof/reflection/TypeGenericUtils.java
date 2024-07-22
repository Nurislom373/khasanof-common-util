package org.khasanof.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Nurislom
 * @see org.khasanof.reflection
 * @since 4/23/2024 2:24 PM
 */
public abstract class TypeGenericUtils {

    public static Type[] getGenericsFromSuperclass(Class<?> type) {
        return getActualTypeArguments((ParameterizedType) type.getGenericSuperclass());
    }

    public static Class<?> getFirstGenericFromInterface(Class<?> type) {
        return (Class<?>) getGenericsFromInterface(type)[0];
    }

    public static Type[] getGenericsFromInterface(Class<?> type) {
        return getActualTypeArguments((ParameterizedType) type.getGenericInterfaces()[0]);
    }

    private static Type[] getActualTypeArguments(ParameterizedType parameterizedType) {
        return parameterizedType != null ? parameterizedType.getActualTypeArguments() : null;
    }
}
