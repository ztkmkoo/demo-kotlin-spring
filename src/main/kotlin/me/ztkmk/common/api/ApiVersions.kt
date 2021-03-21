package me.ztkmk.common.api

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-22 02:19
 */
enum class ApiVersions {
    V1_0{
        override val version: Double = 1.0
    },
    ;

    abstract val version: Double
}