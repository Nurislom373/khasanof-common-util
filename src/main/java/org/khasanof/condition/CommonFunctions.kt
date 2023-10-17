package org.khasanof.condition

import java.util.function.Consumer
import java.util.function.Function

fun <T> ifTrueThen(t: T, checkFunction: Function<T, Boolean>, ifAccept: Consumer<T>, elseRunnable: Runnable) {
    when (checkFunction.apply(t)) {
        true -> ifAccept.accept(t)
        else -> elseRunnable.run()
    }
}

fun <T> ifTrueThen(t: T, checkFunction: Function<T, Boolean>, ifRunnable: Runnable, elseRunnable: Runnable) {
    when (checkFunction.apply(t)) {
        true -> ifRunnable.run()
        else -> elseRunnable.run()
    }
}
