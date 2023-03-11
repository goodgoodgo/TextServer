package org.textin.service;

import org.textin.model.entity.Income;
import org.textin.model.result.ResultModel;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 08:57
 */

public interface IncomeService {
    ResultModel<String>save(Income income);
    ResultModel<String> delete(Long id);
    ResultModel<String> update(Income income);
}
