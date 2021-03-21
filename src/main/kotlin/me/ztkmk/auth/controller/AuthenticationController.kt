package me.ztkmk.auth.controller

import me.ztkmk.auth.entity.GetUserStatusResponse
import me.ztkmk.auth.service.AuthenticationService
import me.ztkmk.common.api.Version
import me.ztkmk.common.log.CustomLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:40
 */
@Controller(value = "/auth")
class AuthenticationController(@Autowired val authenticationService: AuthenticationService) {

    companion object: CustomLog

    @Version(value = [1.0])
    @GetMapping("user/status")
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
}