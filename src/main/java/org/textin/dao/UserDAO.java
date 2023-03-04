package org.textin.dao;

import org.textin.model.entity.User;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 15:33
 */
public interface UserDAO {

    User findById(Long id);

    void insert(User user);

    User findUserByEmail(String email);
}
