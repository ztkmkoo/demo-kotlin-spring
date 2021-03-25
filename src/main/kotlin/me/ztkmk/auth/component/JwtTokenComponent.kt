package me.ztkmk.auth.component

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.constraints.NotEmpty

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-25 22:41
 */
@Component
class JwtTokenComponent {
    val key = "HELLO WORLD"
    val keyBytes = key.toByteArray(Charsets.UTF_8)

    fun parse(@NotEmpty token: String): Claims {
        return Jwts
            .parser()
            .setSigningKey(keyBytes)
            .parseClaimsJws(token)
            .body
    }

    fun validToken(claims: Claims): Boolean {
        return Date().before(claims.expiration)
    }
}