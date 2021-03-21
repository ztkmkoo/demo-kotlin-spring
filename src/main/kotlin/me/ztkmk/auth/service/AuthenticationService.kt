package me.ztkmk.auth.service

import me.ztkmk.auth.enumeration.UserAuthenticationStatus

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:48
 */

interface AuthenticationService {
    fun getUserStatus(cellphone: String, deviceId: String): UserAuthenticationStatus
}