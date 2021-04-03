package me.ztkmk.auth.component

import me.ztkmk.auth.component.JwtRequestFilter.Companion.PATH_AUTH_PATTERN
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 18:56
 */
internal class JwtRequestFilterTest{

    @Test
    fun testPathAuthPattern() {
        val uri = "/api/v1.0/auth/user/certification-number"

        val match = PATH_AUTH_PATTERN.matchEntire(uri)
        assertNotNull(match)
    }

    @Test
    fun testPathAuthPatternNotMatched() {
        val uri = "/api/v1.0/diary/my/list"

        val match = PATH_AUTH_PATTERN.matchEntire(uri)
//        assertFalse(match)
        assertNull(match)
    }
}