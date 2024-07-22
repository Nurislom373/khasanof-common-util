package org.khasanof.primitives;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurislom
 * @see org.khasanof.primitives
 * @since 7/22/2024 2:00 PM
 */
public abstract class PrimitiveUtils {

    private static final Map<String, Class<?>> primitivesMap = new HashMap<>();

    static {
        primitivesMap.put("byte", Byte.class);
        primitivesMap.put("short", Short.class);
        primitivesMap.put("int", Integer.class);
        primitivesMap.put("long", Long.class);
        primitivesMap.put("float", Float.class);
        primitivesMap.put("double", Float.class);
        primitivesMap.put("char", Character.class);
        primitivesMap.put("boolean", Boolean.class);
    }

    /**
     *
     * @param primitiveClass
     * @return
     */
    public static boolean isPrimitiveClass(String primitiveClass) {
        return primitivesMap.containsKey(primitiveClass);
    }

    /**
     *
     * @param primitiveClass
     * @return
     */
    public static Class<?> primitiveToWrapper(String primitiveClass) {
        return primitivesMap.get(primitiveClass);
    }
}
