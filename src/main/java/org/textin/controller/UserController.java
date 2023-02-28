package org.textin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.textin.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 08:22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("code")
    public String sendCode(@RequestParam("email") String email, HttpSession httpSession){

        return userService.sendCode(email,httpSession);
    }
}
