package org.textin.service;

import com.alibaba.fastjson.JSONObject;
import org.textin.model.entity.User;
import org.textin.model.dto.UserDTO;
import org.textin.model.result.ResultModel;
import org.textin.model.vo.UserVO;

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
    ResultModel<JSONObject> get(String token);
    ResultModel<String> sendCode(String email);
    ResultModel<String>  login(UserDTO loginForm, HttpServletRequest httpRequest);
    ResultModel<String>  register(UserDTO registerForm);
}
