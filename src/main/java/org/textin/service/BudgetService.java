package org.textin.service;

import java.math.BigDecimal;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-05 10:35
 */
public interface BudgetService {
    BigDecimal findByIdAndDate(Long userID,String date);
}
