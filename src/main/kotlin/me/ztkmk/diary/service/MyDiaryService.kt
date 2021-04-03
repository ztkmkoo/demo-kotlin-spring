package me.ztkmk.diary.service

import me.ztkmk.diary.entity.MyDiaryInfo
import org.springframework.web.multipart.MultipartFile

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-27 00:39
 */
interface MyDiaryService {
    fun getMyDiaryList(userSeq: Long, page: Int, perPage: Int): List<MyDiaryInfo>

    fun createNewPost(userSeq: Long, tags: List<String>?): Long

    fun uploadFile(userSeq: Long, postSeq: Long, imageFile: MultipartFile): Long
}