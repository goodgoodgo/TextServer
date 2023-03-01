package org.textin.service;

import org.textin.model.entity.User;
import org.textin.model.dto.UserDTO;

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
    User get(Long id);

    String sendCode(String email);

    String login(UserDTO loginForm, HttpServletRequest httpRequest);

    String register(UserDTO registerForm);
}
