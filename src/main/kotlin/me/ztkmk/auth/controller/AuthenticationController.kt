package me.ztkmk.auth.controller

import me.ztkmk.auth.entity.GetUserStatusResponse
import me.ztkmk.auth.entity.PostUserCertificationNumberRequest
import me.ztkmk.auth.service.AuthenticationService
import me.ztkmk.common.api.ApiVersions
import me.ztkmk.common.api.Version
import me.ztkmk.common.log.CustomLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:40
 */
@Controller(value = "/auth")
class AuthenticationController(@Autowired val authenticationService: AuthenticationService) {

    companion object: CustomLog {
        const val COMMON_ERROR_MESSAGE = "Some problems wer encountered in server. Try it later."
    }

    @Version(value = [ApiVersions.V1_0])
    @GetMapping(value = ["user/status"])
    fun getUserStatus(
        @RequestParam(value = "cellphone", required = true) cellphone: String,
        @RequestParam(value = "device-id", required = true) deviceId: String
    ): ResponseEntity<Any> {
        logger.debug("getUserStatus: [UserId: $cellphone][DeviceId: $deviceId]")

        val status = authenticationService.getUserStatus(cellphone, deviceId)

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(GetUserStatusResponse(
                status = status,
                action = status.action,
                message = status.message
            ))
    }

    @Version(value = [ApiVersions.V1_0])
    @PostMapping(value = ["/user/certification-number"])
    fun postUserCertificationNumber(@RequestBody(required = true) request: PostUserCertificationNumberRequest): ResponseEntity<Any> {
        val result = authenticationService.createUserCertificationNumber(request.cellphone, request.deviceId)
        return ResponseEntity
            .status(result)
            .build()
    }
}