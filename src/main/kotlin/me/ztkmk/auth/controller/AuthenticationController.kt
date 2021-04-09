package me.ztkmk.auth.controller

import me.ztkmk.auth.component.JwtTokenComponent
import me.ztkmk.auth.entity.*
import me.ztkmk.auth.enumeration.AuthVerificationStatus
import me.ztkmk.auth.service.AuthenticationService
import me.ztkmk.common.api.ApiVersions
import me.ztkmk.common.api.Version
import me.ztkmk.common.api.entity.CommonApiErrorResponse
import me.ztkmk.common.log.CustomLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:40
 */
@RestController
@RequestMapping(value = ["/auth"])
class AuthenticationController(
    @Autowired val authenticationService: AuthenticationService,
    @Autowired val jwtTokenComponent: JwtTokenComponent
) {

    companion object: CustomLog

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

    @Version(value = [ApiVersions.V1_0])
    @PostMapping(value = ["/user/token"])
    fun createUserToken(@RequestBody(required = true) request: CreateUserTokenRequest): ResponseEntity<Any> {
        val verify = authenticationService.verifyAuthNumber(request.cellphone, request.deviceId, request.authNumber)
        if(AuthVerificationStatus.OK != verify) {
            return ResponseEntity
                .badRequest()
                .body(CommonApiErrorResponse(message = "Create user token error: $verify"))
        }

        val token = authenticationService.createJwtToken(request.cellphone, request.deviceId)

        return ResponseEntity
            .ok()
            .body(CreateUserTokenResponse(token = token))
    }

    @Version(value = [ApiVersions.V1_0])
    @GetMapping(value = ["/user/token/{token}/expiry-date"])
    fun getTokenExpiryDate(@PathVariable(required = true, value = "token") token: String): ResponseEntity<Any> {
        val expiryDate = authenticationService.getUserTokenExpiryDate(token)
        if (expiryDate != null) {
            val refreshable = jwtTokenComponent.refreshableToken(expiryDate)
            return ResponseEntity.ok(GetTokenExpiryDateResponse(expiryDate, refreshable))
        }

        return ResponseEntity.badRequest().build()
    }
}