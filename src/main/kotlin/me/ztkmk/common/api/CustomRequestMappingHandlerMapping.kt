package me.ztkmk.common.api

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 03:18
 */
@Configuration
class CustomRequestMappingHandlerMapping {
    @Bean
    fun webMvcRegistrations(): WebMvcRegistrations {
        return object : WebMvcRegistrations {
            override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
                return ApiVersionRequestMappingHandlerMapping()
            }
        }
    }
}