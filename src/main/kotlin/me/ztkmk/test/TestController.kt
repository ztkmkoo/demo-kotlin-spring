package me.ztkmk.test

import me.ztkmk.common.api.Version
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-15 01:55
 */
@Controller("/")
class TestController {

    @GetMapping("/")
    fun index(model: Model) : String {
        model["title"] = "Kebron"
        return "index"
    }

    @Version(value = [1.0])
    @GetMapping("test")
    fun test() : ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .build()
    }
}