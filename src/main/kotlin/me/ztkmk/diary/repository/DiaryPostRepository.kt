package me.ztkmk.diary.repository

import me.ztkmk.diary.model.DiaryPost
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 17:52
 */
@Repository
interface DiaryPostRepository: JpaRepository<DiaryPost, Long> {
    fun findByUserSeqOrderBySeqDesc(userSeq: Long, pageable: Pageable): List<DiaryPost>
}