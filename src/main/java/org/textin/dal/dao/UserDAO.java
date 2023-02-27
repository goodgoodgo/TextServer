package org.textin.dal.dao;

import org.textin.dal.dataobject.UserDO;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 15:33
 */
public interface UserDAO {
    UserDO get(Long id);
}
