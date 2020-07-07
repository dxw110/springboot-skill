package com.dxw.springbootsecurity.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DCool
 * date 2020-07-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {


    @GetMapping("/test")
    @PreAuthorize("@pms.check('silook')")
    public String test() {
        return "你有瞪死你的权限才能访问";
    }

    @GetMapping
    @PreAuthorize("@pms.check('look')")
    public String test1() {
        return "你有看的权限，然后访问";
    }
}
