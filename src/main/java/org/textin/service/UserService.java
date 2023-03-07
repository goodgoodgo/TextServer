package org.textin.service;

import org.textin.model.entity.User;
import org.textin.model.dto.UserDTO;
import org.textin.model.result.ResultModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:21
 */
public interface UserService {
    ResultModel<User> get(Long id);

    ResultModel<String> sendCode(String email);

    ResultModel<String>  login(UserDTO loginForm, HttpServletRequest httpRequest);

    ResultModel<String>  register(UserDTO registerForm);
}
