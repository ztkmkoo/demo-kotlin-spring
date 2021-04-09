package me.ztkmk.auth.entity

import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-10 02:41
 */
data class GetTokenExpiryDateResponse(
    val expiryDate: Date,
    val refreshable: Boolean
)
