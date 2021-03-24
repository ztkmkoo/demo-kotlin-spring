package me.ztkmk.auth.entity

import javax.validation.constraints.NotEmpty

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 23:05
 */
data class CreateUserTokenRequest(
    @NotEmpty
    val cellphone: String,
    @NotEmpty
    val deviceId: String,
    @NotEmpty
    val authNumber: String
)
