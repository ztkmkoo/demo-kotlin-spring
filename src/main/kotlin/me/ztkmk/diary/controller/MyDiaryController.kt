package me.ztkmk.diary.controller

import me.ztkmk.auth.entity.DefaultAuthenticationToken
import me.ztkmk.common.api.ApiVersions
import me.ztkmk.common.api.Version
import me.ztkmk.diary.entity.GetDiaryListResponse
import me.ztkmk.diary.service.DefaultMyDiaryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-27 00:24
 */
@RestController
@RequestMapping(value = ["/diary"])
class MyDiaryController(
    @Autowired val myDiaryService: DefaultMyDiaryService
) {

    @Version(value = [ApiVersions.V1_0])
    @GetMapping(value = ["/my/list"])
    fun getDiaryList(
        @RequestParam(value = "page", required = true) page: Int,
        @RequestParam(value = "per-page", required = true) perPage: Int,
        token: DefaultAuthenticationToken
    ): ResponseEntity<Any> {
        token.seq
        val userSeq: Long = token.seq
        val list = myDiaryService.getMyDiaryList(userSeq, page, perPage)
        val total = list.size;

        return ResponseEntity
            .ok()
            .body(GetDiaryListResponse(
                total = total,
                list = list
            ))
    }
}