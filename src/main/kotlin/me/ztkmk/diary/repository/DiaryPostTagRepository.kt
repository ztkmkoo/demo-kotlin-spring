package me.ztkmk.diary.repository

import me.ztkmk.diary.model.DiaryPostTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 22:06
 */
@Repository
interface DiaryPostTagRepository: JpaRepository<DiaryPostTag, Long> {
}