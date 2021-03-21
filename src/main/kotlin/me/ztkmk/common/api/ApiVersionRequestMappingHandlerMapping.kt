package me.ztkmk.common.api

import me.ztkmk.common.log.CustomLog
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.util.StringUtils
import org.springframework.web.servlet.mvc.condition.*
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.Method
import java.util.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 02:34
 */
class ApiVersionRequestMappingHandlerMapping: RequestMappingHandlerMapping() {

    companion object: CustomLog {
        const val PREFIX = "api/v"
    }

    override fun getMappingForMethod(method: Method, handlerType: Class<*>): RequestMappingInfo? {
        var info = super.getMappingForMethod(method, handlerType)
        if (Objects.isNull(info)) return null

        val methodAnnotation = AnnotationUtils.findAnnotation(method, Version::class.java)
        if(Objects.isNull(methodAnnotation)) {
            val typeAnnotation = AnnotationUtils.findAnnotation(handlerType, Version::class.java)
            if(Objects.nonNull(typeAnnotation)) {
                val typeCondition = getCustomTypeCondition(handlerType)
                info = createApiVersionInfo(methodAnnotation, typeCondition).combine(info)
            }
        } else {
            val methodCondition = getCustomMethodCondition(method)
            info = createApiVersionInfo(methodAnnotation, methodCondition).combine(info)
        }

        logger.info("mapping: $info")
        return info
    }

    private fun createApiVersionInfo(annotation: Version, condition: RequestCondition<*>?): RequestMappingInfo {
        val values = annotation.value
        val prefix = annotation.prefix
        val patterns = arrayOfNulls<String>(values.size)

        for (i in values.indices) {
            if (StringUtils.isEmpty(prefix)) {
                patterns[i] = PREFIX + values[i].version
            } else {
                patterns[i] = prefix + values[i].version
            }
        }

        return RequestMappingInfo(
            PatternsRequestCondition(patterns, urlPathHelper, pathMatcher, useSuffixPatternMatch(), useTrailingSlashMatch(), fileExtensions),
            RequestMethodsRequestCondition(),
            ParamsRequestCondition(),
            HeadersRequestCondition(),
            ConsumesRequestCondition(),
            ProducesRequestCondition(),
            condition
        );
    }
}