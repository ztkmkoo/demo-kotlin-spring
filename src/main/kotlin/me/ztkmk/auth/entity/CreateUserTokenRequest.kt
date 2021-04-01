package me.ztkmk.auth.entity

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 23:05
 */
data class CreateUserTokenRequest(
    val cellphone: String,
    val deviceId: String,
    val authNumber: String
)
