package me.ztkmk.diary.entity

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-27 02:40
 */
data class GetDiaryListResponse(
    val total: Int,
    val list: List<MyDiaryInfo>
)
