package me.ztkmk.common.util

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 03:25
 */
class RandomUtils {
    companion object {
        private const val RANDOM_LENGTH = 6
        private val NUMERIC_POOL: IntRange = 0..9

        fun randomNumericString(): String {
            return (1..RANDOM_LENGTH).joinToString(separator = "") { NUMERIC_POOL.random().toString() }
        }
    }
}