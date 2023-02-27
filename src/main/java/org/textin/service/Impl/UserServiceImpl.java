package org.textin.service.Impl;

import org.springframework.stereotype.Service;
import org.textin.dal.dao.UserDAO;
import org.textin.model.entity.User;
import org.textin.model.transfer.UserTransfer;
import org.textin.service.UserService;

import javax.annotation.Resource;

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
}
