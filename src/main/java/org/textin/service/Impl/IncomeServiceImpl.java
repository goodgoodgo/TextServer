package org.textin.service.Impl;

import org.textin.model.entity.Income;
import org.textin.model.result.ResultModel;
import org.textin.service.IncomeService;

import java.util.Date;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 10:42
 */
public class IncomeServiceImpl implements IncomeService {



    @Override
    public ResultModel<Boolean> save(Income income) {
        return null;
    }

    @Override
    public ResultModel<Boolean> delete(Income income) {
        return null;
    }

    @Override
    public ResultModel<String> findIncomeByDate(Date date) {
        return null;
    }
}
