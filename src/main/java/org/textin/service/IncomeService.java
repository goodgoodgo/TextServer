package org.textin.service;

import org.springframework.stereotype.Service;
import org.textin.model.entity.Income;
import org.textin.model.result.ResultModel;

import java.util.Date;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 08:57
 */
@Service
public interface IncomeService {

    /**
     * 保存一条收入
     * @return
     */
    ResultModel<Boolean> save(Income income);

    ResultModel<Boolean> delete(Income income);

    ResultModel<String> findIncomeByDate(Date date);
}
