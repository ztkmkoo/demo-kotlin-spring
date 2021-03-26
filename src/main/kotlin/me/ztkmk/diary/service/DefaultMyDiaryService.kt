package me.ztkmk.diary.service

import me.ztkmk.diary.entity.MyDiaryInfo
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-27 00:39
 */
@Service
class DefaultMyDiaryService: MyDiaryService {
    override fun getMyDiaryList(userSeq: Long, page: Int, perPage: Int): List<MyDiaryInfo> {
        val list = ArrayList<MyDiaryInfo>()
        val now = Date()
        list.add(MyDiaryInfo(
            seq = 1,
            imageUrl = "https://miro.medium.com/max/2000/1*w5RcRixCmBD8ZHh875LgKA.png",
            authorSeq = userSeq,
            authorName = "Kebron",
            created = now,
            modified = now
        ))

        return list
    }
}