package me.ztkmk.diary.model

import java.util.*
import javax.persistence.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 17:41
 */
@Entity(name = "diary_posts")
data class DiaryPost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long? = null,
    val userSeq: Long,
    @Column(insertable = false, updatable = false)
    val created: Date? = null,
    @Column(insertable = false, updatable = false)
    val modified: Date? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], targetEntity = DiaryPostTag::class, mappedBy = "postSeq")
    val tags: List<DiaryPostTag>? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], targetEntity = DiaryPostImage::class, mappedBy = "postSeq")
    val images: List<DiaryPostImage>? = null,
)
