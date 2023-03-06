package org.textin.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.model.entity.Budget;


/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-03 16:36
 */
public interface BudgetDAO {
    Budget findByIdAndBudgetDate(@Param("useId") Long userId, @Param("BudgetDate")String BudgetDate);
}
