package me.ztkmk.diary.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 17:43
 */
@Entity(name = "diary_post_tags")
data class DiaryPostTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long? = null,
    val postSeq: Long,
    val tag: String
)
