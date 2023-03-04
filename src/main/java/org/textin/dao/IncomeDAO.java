package org.textin.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.model.entity.Income;

import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 10:44
 */
public interface IncomeDAO {
    void insert(Income income);

    void update(Income income);

    List<Income> findById(@Param("ledgerId") Long ledgerId,@Param("creatorId") Long creatorId,@Param("incomeDate") String date);

    Double sumByDateAndId(@Param("creatorId") Long id,@Param("ledgerId") Long userID,@Param("incomeDate") String date);
}
