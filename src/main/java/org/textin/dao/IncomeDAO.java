package org.textin.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.model.entity.Income;

import java.math.BigDecimal;
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

    /**
     * 求当日收入和
     * @param id
     * @param userID
     * @param date
     * @return
     */
    Double sumByDateAndId(@Param("ledgerId") Long id,@Param("creatorId") Long userID,@Param("incomeDate") String date);

    /**
     * 求某天的收入和
     * @param userID
     * @param date
     * @return
     */
    BigDecimal sumByDateAndUserId(@Param("creatorId") Long userID, @Param("incomeDate") String date);

    /**
     * 求某月收入和
     * @param userID
     * @param date
     * @return
     */
    BigDecimal sumByMonth(@Param("creatorId") Long userID, @Param("incomeDate") String date);
    /**
     * 查找一段时间内收入数据
     * @param id
     * @param startDay
     * @param endDay
     * @return
     */
    List<Income> findBetweenDate(@Param("creatorId") Long id,@Param("startDay") String startDay,@Param("endDay")String endDay);


}
