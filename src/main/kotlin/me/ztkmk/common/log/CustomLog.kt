package me.ztkmk.common.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 03:29
 */
interface CustomLog {
    val logger: Logger get() = LoggerFactory.getLogger(this.javaClass)
}