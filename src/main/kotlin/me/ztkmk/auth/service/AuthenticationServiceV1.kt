package me.ztkmk.auth.service

import me.ztkmk.auth.enumeration.UserAuthenticationStatus
import me.ztkmk.auth.model.UserAuthLog
import me.ztkmk.auth.repository.UserAuthLogRepository
import me.ztkmk.auth.repository.UserRepository
import me.ztkmk.common.log.CustomLog
import me.ztkmk.common.util.RandomUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:49
 */
@Service
class AuthenticationServiceV1(
    @Autowired val userRepository: UserRepository,
    @Autowired val userAuthLogRepository: UserAuthLogRepository
    ): AuthenticationService {

    companion object: CustomLog

    override fun getUserStatus(cellphone: String, deviceId: String): UserAuthenticationStatus {
        val logSuffix = "[Cellphone: $cellphone][DeviceId: $deviceId]"
        logger.debug("Try to get user status. $logSuffix")

        var user = userRepository.findByCellphoneAndUuid(cellphone, deviceId)
        logger.debug("Get user by cellphone and uuid from database: $user. $logSuffix")

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

    override fun createUserCertificationNumber(cellphone: String, deviceId: String): HttpStatus {
        val logSuffix = "[Cellphone: $cellphone][DeviceId: $deviceId]"
        logger.debug("Try to create user certification number. $logSuffix")

        val created = getDatetimeBeforeFiveMinutes()
        val log = userAuthLogRepository.findOneByCellphoneAndCreatedGreaterThan(cellphone, created)
        logger.debug("Get log by cellphone from database: $log. $logSuffix")
        if(Objects.nonNull(log)) {
            return HttpStatus.TOO_MANY_REQUESTS
        }

        val random = RandomUtils.randomNumericString()
        var userAuthLog = UserAuthLog(
            cellphone = cellphone,
            uuid = deviceId,
            number = random
        )

        userAuthLog = userAuthLogRepository.save(userAuthLog)
        logger.debug("Save log: $userAuthLog. $logSuffix")

        return HttpStatus.CREATED
    }

    private fun getDatetimeBeforeFiveMinutes(): Date {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.MINUTE, -5)

        return cal.time
    }
}