package me.ztkmk.auth.config

import me.ztkmk.auth.component.JwtRequestFilter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-25 21:10
 */
@EnableWebSecurity
class WebSecurityConfig(
    val jwtRequestFilter: JwtRequestFilter
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http
            ?.csrf()?.disable()
            ?.authorizeRequests()
            ?.antMatchers("/api/v1.0/user/**")?.permitAll()
            ?.antMatchers("/**")?.hasRole("USER")
            ?.anyRequest()?.authenticated()
            ?.and()
            ?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http?.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}