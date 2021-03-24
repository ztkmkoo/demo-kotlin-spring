package me.ztkmk.auth.repository

import me.ztkmk.auth.model.UserAuthLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 02:57
 */
@Repository
interface UserAuthLogRepository: JpaRepository<UserAuthLog, Long> {
    fun findOneByCellphoneAndCreatedGreaterThan(cellphone: String, created: Date): UserAuthLog?

    fun findFirstByCellphoneAndUuidOrderBySeqDesc(cellphone: String, uuid: String): UserAuthLog?
}