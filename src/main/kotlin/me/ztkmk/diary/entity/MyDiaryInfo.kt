package me.ztkmk.diary.entity

import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-27 00:31
 */
data class MyDiaryInfo(
    val seq: Long,
    val imageUrls: List<String>?,
    val authorSeq: Long,
    val authorName: String,
    val created: Date,
    val modified: Date
)
