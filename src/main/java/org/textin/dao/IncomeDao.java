package org.textin.dao;

import org.textin.model.entity.Income;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 10:44
 */
public interface IncomeDao {
    void insert(Income income);

    void update(Income income);
}
