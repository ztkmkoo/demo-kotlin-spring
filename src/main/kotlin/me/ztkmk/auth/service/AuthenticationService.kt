package me.ztkmk.auth.service

import me.ztkmk.auth.enumeration.AuthVerificationStatus
import me.ztkmk.auth.enumeration.UserAuthenticationStatus
import org.springframework.http.HttpStatus

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:48
 */

interface AuthenticationService {
    fun getUserStatus(cellphone: String, deviceId: String): UserAuthenticationStatus

    fun createUserCertificationNumber(cellphone: String, deviceId: String): HttpStatus

    fun verifyAuthNumber(cellphone: String, deviceId: String, authNumber: String): AuthVerificationStatus

    fun createJwtToken(cellphone: String, deviceId: String): String
}