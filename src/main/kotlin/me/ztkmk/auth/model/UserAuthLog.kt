package me.ztkmk.auth.model

import java.util.*
import javax.persistence.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 02:53
 */
@Entity(name = "user_auth_logs")
data class UserAuthLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long?,
    val cellphone: String,
    val uuid: String,
    val number: String,
    @Column(insertable = false)
    val created: Date?
) {
    constructor(cellphone: String, uuid: String, number: String) :
            this(null, cellphone, uuid, number, null)
}
