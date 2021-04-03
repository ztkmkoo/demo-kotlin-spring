package me.ztkmk.auth.component

import me.ztkmk.auth.entity.DefaultAuthenticationToken
import me.ztkmk.common.log.CustomLog
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-25 21:42
 */
@Component
class JwtRequestFilter(val jwtTokenComponent: JwtTokenComponent) : OncePerRequestFilter() {

    companion object : CustomLog {
        val PATH_AUTH_PATTERN = Regex("^\\/api\\/v1.0\\/auth(.*)")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestTokenHeader = request.getHeader("Authorization")
        if (!StringUtils.isEmpty(requestTokenHeader) && requestTokenHeader.startsWith("Bearer")) {
            val jwtToken = requestTokenHeader.substring(7)

            val claims = jwtTokenComponent.parse(jwtToken)

            if (jwtTokenComponent.validToken(claims)) {
                val seq = getLongValue(claims["userSeq"])
                if (seq > 0) {
                    val authToken = DefaultAuthenticationToken(seq)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken

                    filterChain.doFilter(request, response)
                } else {
                    throw RuntimeException("Invalid user token")
                }
            } else {
                logger.error("Token is expired: $jwtToken")
            }
        }

        val match = PATH_AUTH_PATTERN.matchEntire(request.requestURI)
        if (Objects.nonNull(match)) {
            filterChain.doFilter(request, response)
        } else {
            throw RuntimeException("Invalid user token. [URI: ${request.requestURI}]")
        }
    }

    private fun getLongValue(any: Any?): Long {
        return when (any) {
            is Number -> any.toLong()
            else -> throw RuntimeException("not numeric!!!")
        }
    }
}