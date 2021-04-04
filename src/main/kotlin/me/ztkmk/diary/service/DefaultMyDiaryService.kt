package me.ztkmk.diary.service

import me.ztkmk.common.log.CustomLog
import me.ztkmk.diary.entity.MyDiaryInfo
import me.ztkmk.diary.model.DiaryPost
import me.ztkmk.diary.model.DiaryPostImage
import me.ztkmk.diary.model.DiaryPostTag
import me.ztkmk.diary.repository.DiaryPostImageRepository
import me.ztkmk.diary.repository.DiaryPostRepository
import me.ztkmk.diary.repository.DiaryPostTagRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-27 00:39
 */
@Service
class DefaultMyDiaryService(
    val diaryPostRepository: DiaryPostRepository,
    val diaryPostTagRepository: DiaryPostTagRepository,
    val diaryPostImageRepository: DiaryPostImageRepository,
    @Value(value = "\${diary.post.image.base-path}")
    val diaryPostImageBasePath: String,
): MyDiaryService {
    companion object: CustomLog {
        val CONTENT_TYPE_IMAGE_PATTERN = Regex("img\\/.*")
        val FILENAME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
    }

    override fun getMyDiaryList(userSeq: Long, page: Int, perPage: Int): List<MyDiaryInfo> {
        val pageable = PageRequest.of(page - 1, perPage)
        val postList = diaryPostRepository.findByUserSeqOrderBySeqDesc(userSeq, pageable)
        return postList.map { post -> convertPostModelToView(post) }
    }

    @Transactional
    override fun createNewPost(userSeq: Long, tags: List<String>?): Long {
        val post = DiaryPost(userSeq = userSeq)
        diaryPostRepository.save(post)

        val tagModelList = tagModelFromTagList(post.seq!!, tags)
        diaryPostTagRepository.saveAll(tagModelList)
        return post.seq!!
    }

    override fun uploadFile(userSeq: Long, postSeq: Long, imageFile: MultipartFile): Long {
        if (validImageFile(imageFile)) {
            throw RuntimeException("It is not a image file")
        }

        val directoryPath = createImageDirectoryPath(postSeq)
        val directory = File(diaryPostImageBasePath, directoryPath)
        directory.deleteRecursively()
        directory.mkdirs()

        val filename = createFilename(imageFile.originalFilename!!)
        val file = File(directory, filename)
        imageFile.transferTo(file)
        logger.info("Save uploaded file to ${file.absolutePath}")

        val url = "/images/$directoryPath/$filename"
        logger.info("Uploaded file url: $url")

        val image = DiaryPostImage(null, postSeq, url)
        diaryPostImageRepository.save(image)

        return image.seq!!
    }

    private fun convertPostModelToView(post: DiaryPost): MyDiaryInfo {
        return MyDiaryInfo(
          seq = post.seq!!,
          imageUrls = post.images?.map { image -> image.url },
          authorSeq = post.userSeq,
          authorName = "Kebron",
          created = post.created!!,
          modified = post.modified!!
        );
    }

    private fun tagModelFromTagList(postSeq: Long, tags: List<String>?): List<DiaryPostTag>? {
        if(CollectionUtils.isEmpty(tags)) {
            return null;
        }

        return tags!!
            .map { tag: String -> DiaryPostTag(
                postSeq = postSeq,
                tag = tag
            ) }
    }

    private fun validImageFile(imageFile: MultipartFile): Boolean {
        if(StringUtils.isEmpty(imageFile.contentType) || StringUtils.isEmpty(imageFile.originalFilename)) {
            return false
        }

        val match = CONTENT_TYPE_IMAGE_PATTERN.matchEntire(imageFile.contentType!!)
        return Objects.nonNull(match)
    }

    private fun createImageDirectoryPath(postSeq: Long): String {
        return "posts/$postSeq"
    }

    private fun createFilename(originalFilename: String): String {
        val now = LocalDateTime.now()
        val filename = now.format(FILENAME_FORMATTER)

        val splits = originalFilename.split(".")
        if(Objects.isNull(splits) || splits.size <= 1) {
            return filename
        }

        return filename + "." + splits[splits.size - 1]
    }
}