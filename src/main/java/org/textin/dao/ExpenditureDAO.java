package org.textin.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-03 12:22
 */
public interface ExpenditureDAO {
    List<Expenditure> findById(@Param("creatorId") Long id,@Param("ledgerId") Long userID,@Param("expenditureDate") String date);
    List<Expenditure> findByIdAndMonth(@Param("creatorId") Long id,@Param("expenditureDate") String date);
    List<Expenditure> findBetweenDate(@Param("creatorId") Long id, @Param("startDay") String startDay, @Param("endDay")String endDay);
    Double sumByDateAndId(@Param("ledgerId") Long id,@Param("creatorId") Long userID,@Param("expenditureDate") String date);
    BigDecimal sumByDateAndUserId(@Param("creatorId") Long userID, @Param("expenditureDate") String date);
    BigDecimal sumByMonth(@Param("creatorId") Long userID, @Param("expenditureDate") String date);
}
