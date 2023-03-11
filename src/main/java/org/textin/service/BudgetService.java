package org.textin.service;

import com.alibaba.fastjson.JSONObject;
import org.textin.model.dto.BudgetDTO;
import org.textin.model.result.ResultModel;


/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-05 10:35
 */
public interface BudgetService {
    ResultModel<JSONObject> getYearBudget(Long userID,String year);
    ResultModel<String> update(BudgetDTO budgetDTO);
}
