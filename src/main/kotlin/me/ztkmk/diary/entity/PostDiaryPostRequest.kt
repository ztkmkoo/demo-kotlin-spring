package me.ztkmk.diary.entity

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 17:32
 */
data class PostDiaryPostRequest(
    val tags: List<String>?
) {
    constructor(): this(null)
}
