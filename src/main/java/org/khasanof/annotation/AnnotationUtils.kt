package org.khasanof.annotation

import org.khasanof.base.BaseUtils
import java.lang.reflect.Method
import java.util.*

fun hasAnnotation(method: Method, annotation: Class<out Annotation>, isSuper: Boolean): Boolean {
    BaseUtils.checkArgsIsNonNull(method, annotation, isSuper)
    if (isSuper) {
        return Arrays.stream(method.annotations)
            .anyMatch { any ->
                Objects.equals(any.annotationClass, annotation)
            }
    }
    return method.isAnnotationPresent(annotation)
}

fun hasAnnotation(method: Method, annotation: Class<out Annotation>): Boolean {
    return method.isAnnotationPresent(annotation)
}

fun getAnnotation(method: Method, annotation: Class<out Annotation?>, isSuper: Boolean): Annotation? {
    BaseUtils.checkArgsIsNonNull(method, annotation, isSuper)
    return if (isSuper) {
        Arrays.stream(method.annotations)
            .filter { f: Annotation? ->
                f?.javaClass?.componentType()!!.isAnnotationPresent(annotation)
            }
            .findFirst().orElse(null)
    } else method.getAnnotation(annotation)
}

fun getAnnotation(method: Method, annotation: Class<out Annotation>): Annotation? {
    return Arrays.stream(method.annotations)
        .filter {
            it.javaClass.componentType.equals(annotation)
        }
        .findFirst().orElse(null)
}
