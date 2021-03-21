package me.ztkmk.common.api

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 02:28
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.TYPE])
annotation class Version(
    val value: DoubleArray,
    val prefix: String = "api/v"
)
