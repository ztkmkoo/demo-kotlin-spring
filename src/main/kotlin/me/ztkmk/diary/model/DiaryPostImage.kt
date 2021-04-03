package me.ztkmk.diary.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 22:58
 */
@Entity(name = "diary_post_images")
data class DiaryPostImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long?,
    val postSeq: Long,
    val url: String
)
