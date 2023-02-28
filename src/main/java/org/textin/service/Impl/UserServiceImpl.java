package org.textin.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.textin.dal.dao.UserDAO;
import org.textin.model.entity.User;
import org.textin.model.enums.ErrorCodeEn;
import org.textin.model.transfer.UserTransfer;
import org.textin.service.UserService;
import org.textin.util.CheckUtil;
import org.textin.util.MailUtils;
import org.textin.util.ResultModelUtil;
import org.textin.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:33
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDao;

    @Override
    public User get(Long id) {
        User user= UserTransfer.toUser(userDao.get(id));
        return user;
    }

    @Override
    public String sendCode(String email, HttpSession httpSession) {
        CheckUtil.isPhoneNumberValid(email, ErrorCodeEn.EMAIL_NOT_VAlID);
        String code= StringUtil.generateCode();
        httpSession.setAttribute("code",code);
        MailUtils.sendEmail(code,email);
        return JSON.toJSONString(ResultModelUtil.success("验证码发生成功"));
    }
}
