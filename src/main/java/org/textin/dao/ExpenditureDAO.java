package org.textin.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.model.entity.Expenditure;

import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-03 12:22
 */
public interface ExpenditureDAO {
    List<Expenditure> findById(@Param("creatorId") Long id,@Param("ledgerId") Long userID,@Param("expenditureDate") String date);

    Double sumByDateAndId(@Param("creatorId") Long id,@Param("ledgerId") Long userID,@Param("expenditureDate") String date);
}
