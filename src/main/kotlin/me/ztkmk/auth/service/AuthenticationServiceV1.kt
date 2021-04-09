package me.ztkmk.auth.service

import me.ztkmk.auth.component.JwtTokenComponent
import me.ztkmk.auth.enumeration.AuthVerificationStatus
import me.ztkmk.auth.enumeration.UserAuthenticationStatus
import me.ztkmk.auth.model.User
import me.ztkmk.auth.model.UserAuthLog
import me.ztkmk.auth.repository.UserAuthLogRepository
import me.ztkmk.auth.repository.UserRepository
import me.ztkmk.common.log.CustomLog
import me.ztkmk.common.util.RandomUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.security.InvalidParameterException
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:49
 */
@Service
class AuthenticationServiceV1(
    @Autowired val userRepository: UserRepository,
    @Autowired val userAuthLogRepository: UserAuthLogRepository,
    @Autowired val jwtTokenComponent: JwtTokenComponent
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
        logger.info("RANDOM: $random. $logSuffix")
        var userAuthLog = UserAuthLog(
            cellphone = cellphone,
            uuid = deviceId,
            number = random
        )

        userAuthLog = userAuthLogRepository.save(userAuthLog)
        logger.debug("Save log: $userAuthLog. $logSuffix")

        return HttpStatus.CREATED
    }

    override fun verifyAuthNumber(cellphone: String, deviceId: String, authNumber: String): AuthVerificationStatus {
        if(StringUtils.isEmpty(cellphone) || StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(authNumber)) {
            throw InvalidParameterException();
        }

        val log = userAuthLogRepository.findFirstByCellphoneAndUuidOrderBySeqDesc(cellphone = cellphone, uuid = deviceId)
        if(Objects.isNull(log)) {
            throw InvalidParameterException();
        }

        val expired = verifyAuthNumberRequestExpired((log!!.created!!))
        if(expired) {
            return AuthVerificationStatus.EXPIRED
        }

        if (authNumber != log.number) {
            return AuthVerificationStatus.INVALID_NUMBER
        }

        return AuthVerificationStatus.OK
    }

    override fun createJwtToken(cellphone: String, deviceId: String): String {
        var user = userRepository.findByCellphone(cellphone)
        val now = Date()

        if(Objects.isNull(user)) {
            val idno = UUID.randomUUID()
            user = User(
                seq = null,
                idNo = idno.toString(),
                cellphone = cellphone,
                uuid = deviceId,
                created = now,
                modified = now
            )

            userRepository.save(user)
        }

        return jwtTokenComponent.newToken(user!!.seq!!, now)
    }

    override fun getUserTokenExpiryDate(token: String): Date? {
        if(StringUtils.isEmpty(token)) return null

        val claims = jwtTokenComponent.parse(token)
        return claims.expiration
    }

    private fun getDatetimeBeforeFiveMinutes(): Date {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.MINUTE, -5)

        return cal.time
    }

    private fun verifyAuthNumberRequestExpired(reqRegYmdt: Date): Boolean {
        val fiveMinBefore = getDatetimeBeforeFiveMinutes()
        return fiveMinBefore.after(reqRegYmdt)
    }
}