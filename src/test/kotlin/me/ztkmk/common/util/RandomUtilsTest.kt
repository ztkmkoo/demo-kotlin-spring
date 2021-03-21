package me.ztkmk.common.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 04:09
 */
internal class RandomUtilsTest {

    @Test
    fun randomNumericStringTest() {
        val randomString = RandomUtils.randomNumericString()
        assertEquals(6, randomString.length)
    }
}