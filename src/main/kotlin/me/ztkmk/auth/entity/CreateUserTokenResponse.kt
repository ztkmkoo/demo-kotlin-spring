package me.ztkmk.auth.entity

import javax.validation.constraints.NotEmpty

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-25 00:57
 */
data class CreateUserTokenResponse(
    @NotEmpty
    val token: String
)
