package org.textin.dal.dao;

import org.textin.dal.dataobject.OptLogDO;

import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 10:41
 */
public interface OptLogDAO {
    void insert(OptLogDO optLogDO);

    List<OptLogDO> query(OptLogDO optLogDO);
}
