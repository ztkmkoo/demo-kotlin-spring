package me.ztkmk.auth.entity

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 17:16
 */
data class DefaultAuthenticationToken(
    val userSeq: Long
): UsernamePasswordAuthenticationToken(userSeq, null, DEFAULT_AUTHORITY_LIST) {
    companion object {
        val DEFAULT_AUTHORITY_LIST =  defaultAuthorityList()

        private fun defaultAuthorityList(): ArrayList<GrantedAuthority> {
            val authList = ArrayList<GrantedAuthority>()
            authList.add(GrantedAuthority(function = {"ROLE_USER"}))
            return authList
        }
    }
}