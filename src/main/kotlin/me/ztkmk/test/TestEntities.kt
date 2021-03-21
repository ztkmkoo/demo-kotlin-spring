package me.ztkmk.test

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 21:12
 */
@Entity(name = "users")
data class User (
    @Id
    val seq: Long,
    @Column(name = "idno")
    val idNo: String,
    val cellphone: String,
    val regYmdt: Date,
    val modYmdt: Date
    )