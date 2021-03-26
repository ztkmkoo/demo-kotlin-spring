package me.ztkmk.diary.service

import me.ztkmk.diary.entity.MyDiaryInfo

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-27 00:39
 */
interface MyDiaryService {
    fun getMyDiaryList(userSeq: Long, page: Int, perPage: Int): List<MyDiaryInfo>
}