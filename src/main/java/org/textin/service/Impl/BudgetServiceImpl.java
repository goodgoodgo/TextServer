package org.textin.service.Impl;

import org.springframework.stereotype.Service;
import org.textin.dao.BudgetDAO;
import org.textin.service.BudgetService;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-05 10:36
 */
@Service
public class BudgetServiceImpl implements BudgetService {
    @Resource
    private BudgetDAO budgetDAO;
    @Override
    public BigDecimal findByIdAndDate(Long userID, String date) {
        BigDecimal budget=BigDecimal.ZERO;
        if(null!=budgetDAO.findByIdAndBudgetDate(userID,date)){
            budget = budgetDAO.findByIdAndBudgetDate(userID,date).getBudget();
        }
        return budget;
    }
}
