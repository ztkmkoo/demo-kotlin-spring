package me.ztkmk.test

import me.ztkmk.common.log.CustomLog
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 22:04
 */
@Transactional
@SpringBootTest
internal class TestRepositoryTest(@Autowired val userRepository: TestRepository) {

    companion object: CustomLog

    @Test
    fun testFindById() {
        val user = userRepository.findById(1)
        assertNotNull(user)

        logger.info("USER: $user")
    }
}