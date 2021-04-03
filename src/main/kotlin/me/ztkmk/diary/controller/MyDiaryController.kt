package me.ztkmk.diary.controller

import me.ztkmk.auth.entity.DefaultAuthenticationToken
import me.ztkmk.common.api.ApiVersions
import me.ztkmk.common.api.Version
import me.ztkmk.diary.entity.GetDiaryListResponse
import me.ztkmk.diary.entity.PostDiaryPostRequest
import me.ztkmk.diary.entity.PostDiaryPostResponse
import me.ztkmk.diary.service.DefaultMyDiaryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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
        val userSeq: Long = token.userSeq
        val list = myDiaryService.getMyDiaryList(userSeq, page, perPage)
        val total = list.size;

        return ResponseEntity
            .ok()
            .body(
                GetDiaryListResponse(
                    total = total,
                    list = list
                )
            )
    }

    @Version(value = [ApiVersions.V1_0])
    @PostMapping(value = ["/post"])
    fun postDiaryPost(
        @RequestBody(required = true) request: PostDiaryPostRequest,
        token: DefaultAuthenticationToken
    ): ResponseEntity<Any> {
        val postSeq = myDiaryService.createNewPost(token.userSeq, request.tags)
        return if (postSeq > 0) {
            ResponseEntity
                .ok()
                .body(PostDiaryPostResponse(postSeq = postSeq))
        } else {
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build()
        }
    }

    @Version(value = [ApiVersions.V1_0])
    @PostMapping(value = ["/posts/{postSeq}/image"])
    fun postDiaryPostImage(
        @RequestParam(required = true, value = "file") image: MultipartFile,
        @PathVariable(required = true, value = "postSeq") postSeq: Long,
        token: DefaultAuthenticationToken
    ): ResponseEntity<Any> {
        myDiaryService.uploadFile(token.userSeq, postSeq, image)
        return ResponseEntity.ok().build()
    }
}