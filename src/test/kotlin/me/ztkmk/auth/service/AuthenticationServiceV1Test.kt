package me.ztkmk.auth.service

import me.ztkmk.auth.enumeration.UserAuthenticationStatus
import me.ztkmk.auth.model.User
import me.ztkmk.auth.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 01:22
 */

class AuthenticationServiceV1Test {

    @InjectMocks
    lateinit var service: AuthenticationServiceV1

    @Mock
    var repository: UserRepository = Mockito.mock(UserRepository::class.java)

    private val unregisteredCellphone = "01056789012"
    private val registeredCellphone = "01012345678"
    private val deviceIdForRegisteredCellphone = "HelloWorld"
    private val unregisteredDeviceId = "RandomDeviceId"
    private val now = Date()
    private val user = User(
        seq = 1,
        idNo = "user1",
        cellphone = registeredCellphone,
        uuid = deviceIdForRegisteredCellphone,
        created = now,
        modified = now
    )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(repository.findByCellphoneAndUuid(registeredCellphone, deviceIdForRegisteredCellphone))
            .thenReturn(user)

        `when`(repository.findByCellphoneAndUuid(registeredCellphone, unregisteredDeviceId))
            .thenReturn(null)

        `when`(repository.findByCellphone(registeredCellphone))
            .thenReturn(user)
    }

    @Test
    fun getUserStatusNewUser() {
        val status = service.getUserStatus(unregisteredCellphone, unregisteredDeviceId)
        assertEquals(UserAuthenticationStatus.NEW_USER, status)
    }

    @Test
    fun getUserStatusRegisteredUser() {
        val status = service.getUserStatus(registeredCellphone, unregisteredDeviceId)
        assertEquals(UserAuthenticationStatus.REGISTERED_USER, status)
    }

    @Test
    fun getUserStatusRegisteredDevice() {
        val status = service.getUserStatus(registeredCellphone, deviceIdForRegisteredCellphone)
        assertEquals(UserAuthenticationStatus.REGISTERED_DEVICE, status)
    }
}