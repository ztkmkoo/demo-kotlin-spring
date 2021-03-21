package me.ztkmk.auth.service

import me.ztkmk.auth.enumeration.UserAuthenticationStatus
import me.ztkmk.auth.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:49
 */
@Service
class AuthenticationServiceV1(@Autowired val userRepository: UserRepository): AuthenticationService {

    override fun getUserStatus(cellphone: String, deviceId: String): UserAuthenticationStatus {
        var user = userRepository.findByCellphoneAndDeviceId(cellphone, deviceId)

        if(Objects.nonNull(user)) {
            return UserAuthenticationStatus.REGISTERED_DEVICE
        }

        user = userRepository.findByCellphone(cellphone)
        if (Objects.nonNull(user)) {
            return UserAuthenticationStatus.REGISTERED_USER
        }

        return UserAuthenticationStatus.NEW_USER
    }
}