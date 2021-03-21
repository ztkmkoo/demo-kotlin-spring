package me.ztkmk.auth.service

import me.ztkmk.auth.enumeration.UserAuthenticationStatus
import me.ztkmk.auth.repository.UserRepository
import me.ztkmk.common.log.CustomLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:49
 */
@Service
class AuthenticationServiceV1(@Autowired val userRepository: UserRepository): AuthenticationService {

    companion object: CustomLog

    override fun getUserStatus(cellphone: String, deviceId: String): UserAuthenticationStatus {
        val logSuffix = "[Cellphone: $cellphone][DeviceId: $deviceId]"
        logger.debug("Try to get user status. $logSuffix")

        var user = userRepository.findByCellphoneAndUuid(cellphone, deviceId)
        logger.debug("Get user by cellphone and device id from database: $user. $logSuffix")

        if(Objects.nonNull(user)) {
            return UserAuthenticationStatus.REGISTERED_DEVICE
        }

        user = userRepository.findByCellphone(cellphone)
        logger.debug("Get user by cellphone from database: $user. $logSuffix")
        if (Objects.nonNull(user)) {
            return UserAuthenticationStatus.REGISTERED_USER
        }

        logger.debug("There is no user match from database. $logSuffix")
        return UserAuthenticationStatus.NEW_USER
    }

    override fun createUserCertificationNumber(cellphone: String, deviceId: String): Boolean {
        TODO("Not yet implemented")
    }
}