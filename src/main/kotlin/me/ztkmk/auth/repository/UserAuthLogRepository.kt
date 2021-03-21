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
//    companion object {
//        const val BASE_COLUMNS = """
//            seq,
//            cellphone,
//            uuid,
//            number,
//            created
//        """
//
//        const val CONDITION_CELLPHONE = "cellphone = :cellphone"
//        const val CONDITION_UUID = "uuid = :uuid"
//        const val CONDITION_CREATED = "created >= :created"
//    }
//
//    @Query(value = "SELECT $BASE_COLUMNS FROM user_auth_logs WHERE $CONDITION_CELLPHONE AND $CONDITION_CREATED")
//    fun findOneByCellphoneIfRequested(@Param("cellphone") cellphone: String, @Param("created") created: Date): UserAuthLog?

    fun findOneByCellphoneAndCreatedGreaterThan(cellphone: String, created: Date): UserAuthLog?
}