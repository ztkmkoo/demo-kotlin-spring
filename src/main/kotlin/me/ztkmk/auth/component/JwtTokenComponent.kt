package me.ztkmk.auth.component

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClaims
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-25 22:41
 */
@Component
class JwtTokenComponent {
    private final val key = "HELLO WORLD"
    private final val keyBytes = key.toByteArray(Charsets.UTF_8)
    private final val oneHourInMillis: Long = 60 * 60 * 1000
    private final val oneDayInMillis: Long = 24 * oneHourInMillis
    private final val validTokenMillis: Long = 30 * oneDayInMillis

    fun parse(token: String): Claims {
        return Jwts
            .parser()
            .setSigningKey(keyBytes)
            .parseClaimsJws(token)
            .body
    }

    fun validToken(claims: Claims): Boolean {
        return Date().before(claims.expiration)
    }

    fun newToken(userSeq: Long, now: Date): String {
        val claims = DefaultClaims()
        claims.expiration = Date(now.time + validTokenMillis)
        claims.issuedAt = now
        claims["userSeq"] = userSeq

        return Jwts
            .builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS256")
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, key.toByteArray(Charsets.UTF_8))
            .compact()
    }

    fun refreshableToken(expiryDate: Date): Boolean {
        val lastValidDate = Date(Date().time + validTokenMillis)
        return lastValidDate.before(expiryDate)
    }
}