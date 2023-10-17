package org.khasanof.trycatch

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

class TryKtTest {

    @Test
    @Disabled
    fun toCallTest() {
        Try.toCall {
            thrower()
        }.ifRaises(RuntimeException::class.java)
            .thenCall { throwable ->
                assertInstanceOf(RuntimeException::class.java, throwable)
                println("Successfully Thrown RuntimeException");
            }.done()
    }

    @Throws(RuntimeException::class)
    private fun thrower() {
        throw RuntimeException("just test")
    }

}
