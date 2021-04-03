package me.ztkmk.diary.repository

import me.ztkmk.diary.model.DiaryPostImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-04-03 23:28
 */
@Repository
interface DiaryPostImageRepository: JpaRepository<DiaryPostImage, Long> {
}