package com.mudrichenkoevgeny.weathertestapp.core.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test

class DoubleExtensionsTest {

    @Test
    fun `oneDecimal rounds down correctly`() {
        // GIVEN
        val value = 3.14
        val expected = 3.1

        // WHEN
        val result = value.oneDecimal()

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `oneDecimal rounds up correctly`() {
        // GIVEN
        val value = 3.16
        val expected = 3.2

        // WHEN
        val result = value.oneDecimal()

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `oneDecimal handles whole numbers`() {
        // GIVEN
        val value = 5.0
        val expected = 5.0

        // WHEN
        val result = value.oneDecimal()

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `oneDecimal handles negative numbers rounding down`() {
        // GIVEN
        val value = -2.34
        val expected = -2.3

        // WHEN
        val result = value.oneDecimal()

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `oneDecimal handles negative numbers rounding up`() {
        // GIVEN
        val value = -2.36
        val expected = -2.4

        // WHEN
        val result = value.oneDecimal()

        // THEN
        assertEquals(expected, result)
    }
}