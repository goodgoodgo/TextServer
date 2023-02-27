package org.textin.dal.dao;

import org.textin.dal.dataobject.IncomeDO;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 10:44
 */
public interface IncomeDAO {
    void insert(IncomeDO incomeDO);

    void update(IncomeDO incomeDO);
}
