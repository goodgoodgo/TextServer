package org.textin.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;
import org.textin.model.dto.UserDTO;
import org.textin.service.UserService;
import org.textin.util.ResultModelUtil;
import org.textin.util.UserHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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
    public String sendCode(@RequestParam("email") String email){

        return userService.sendCode(email);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO loginForm, HttpServletRequest httpRequest){
        return userService.login(loginForm,httpRequest);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDTO register){
        return userService.register(register);
    }

    @GetMapping("/me")
    public String me(){
        return JSON.toJSONString(ResultModelUtil.success(UserHolder.getUser()));
    }


}
