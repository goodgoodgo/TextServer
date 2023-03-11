package org.textin.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.model.entity.Budget;

import java.math.BigDecimal;
import java.util.List;


/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-03 16:36
 */
public interface BudgetDAO {
    Budget findByIdAndBudgetDate(@Param("userId") Long userId, @Param("BudgetDate")String BudgetDate);
    List<Budget> findYearById(@Param("userId") Long id, @Param("year") String year);
    void update(@Param("userId") Long id, @Param("date") String date, @Param("budget") BigDecimal budget);
    void insert(@Param("userId") Long id, @Param("date") String date, @Param("budget") BigDecimal budget);
}
