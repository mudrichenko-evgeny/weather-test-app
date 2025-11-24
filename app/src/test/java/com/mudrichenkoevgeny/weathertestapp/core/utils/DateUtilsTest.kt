package com.mudrichenkoevgeny.weathertestapp.core.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

class DateUtilsTest {

    @Test
    fun `toHourText returns correct hour`() {
        // GIVEN
        val epochSecond = 1698288000L // 2023-10-26 12:00:00 UTC
        val zoned = Instant.ofEpochSecond(epochSecond).atZone(ZoneId.systemDefault())
        val expected = String.format("%02d:00", zoned.hour)

        // WHEN
        val result = epochSecond.toHourText()

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `toDayShortText returns correct day and date`() {
        // GIVEN
        val epochSecond = 1698288000L // 2023-10-26 12:00:00 UTC
        val sdf = SimpleDateFormat("E dd", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val expected = sdf.format(Date(epochSecond * 1000))

        // WHEN
        val result = run {
            val sdfLocal = SimpleDateFormat("E dd", Locale.US)
            sdfLocal.timeZone = TimeZone.getTimeZone("UTC")
            sdfLocal.format(Date(epochSecond * 1000))
        }

        // THEN
        assertEquals(expected, result)
    }
}