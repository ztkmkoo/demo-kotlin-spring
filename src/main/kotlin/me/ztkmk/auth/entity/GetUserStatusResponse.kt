package me.ztkmk.auth.entity

import me.ztkmk.auth.enumeration.AuthenticationAction
import me.ztkmk.auth.enumeration.UserAuthenticationStatus

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 00:20
 */
class GetUserStatusResponse(
    val status: UserAuthenticationStatus,
    val action: AuthenticationAction,
    val message: String?
)