package me.ztkmk.auth.entity

import javax.validation.constraints.NotEmpty

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 02:30
 */
data class PostUserCertificationNumberRequest(
    @NotEmpty
    val cellphone: String,
    @NotEmpty
    val deviceId: String
)