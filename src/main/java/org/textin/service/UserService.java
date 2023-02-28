package org.textin.service;

import org.textin.model.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:21
 */
public interface UserService {
    User get(Long id);

    String sendCode(String phone, HttpSession httpSession);
}
