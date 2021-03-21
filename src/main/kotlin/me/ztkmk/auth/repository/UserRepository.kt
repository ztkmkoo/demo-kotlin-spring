package me.ztkmk.auth.repository

import me.ztkmk.auth.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:54
 */
@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByCellphoneAndDeviceId(cellphone: String, deviceId: String): User?
    fun findByCellphone(cellphone: String): User?
}