package me.ztkmk.test

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
        model.set("title", "Kebron")
        return "index"
    }
}