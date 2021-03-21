package me.ztkmk.test

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 22:01
 */
@Repository
interface TestRepository : JpaRepository<User, Long> {
}