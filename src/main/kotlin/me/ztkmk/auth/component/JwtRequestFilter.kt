package me.ztkmk.auth.component

import me.ztkmk.common.log.CustomLog
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-25 21:42
 */
@Component
class JwtRequestFilter(val jwtTokenComponent: JwtTokenComponent): OncePerRequestFilter() {

    companion object: CustomLog

    val authorityList = defaultAuthorityList()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestTokenHeader = request.getHeader("Authorization")
        if(!StringUtils.isEmpty(requestTokenHeader) && requestTokenHeader.startsWith("Bearer")) {
            val jwtToken = requestTokenHeader.substring(7)

            val claims = jwtTokenComponent.parse(jwtToken)

            if(jwtTokenComponent.validToken(claims)) {
                val seq = claims["userSeq"]
                val authToken = UsernamePasswordAuthenticationToken(seq, null, authorityList)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            } else{
                logger.error("Token is expired: $jwtToken")
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun defaultAuthorityList(): ArrayList<GrantedAuthority> {
        val authList = ArrayList<GrantedAuthority>()
        authList.add(GrantedAuthority(function = {"ROLE_USER"}))
        return authList
    }
}