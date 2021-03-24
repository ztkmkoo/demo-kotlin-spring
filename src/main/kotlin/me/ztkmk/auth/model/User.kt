package me.ztkmk.auth.model

import java.util.*
import javax.persistence.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:57
 */
@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long?,
    @Column(name = "idno")
    val idNo: String,
    val cellphone: String,
    val uuid: String,
    val created: Date,
    val modified: Date
)