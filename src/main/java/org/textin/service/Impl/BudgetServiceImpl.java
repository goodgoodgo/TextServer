package org.textin.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.textin.dao.BudgetDAO;
import org.textin.dao.ExpenditureDAO;
import org.textin.model.dto.BudgetDTO;
import org.textin.model.entity.Budget;
import org.textin.model.result.ResultModel;
import org.textin.model.vo.BudgetVO;
import org.textin.service.BudgetService;
import org.textin.util.ResultModelUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private ExpenditureDAO expenditureDAO;

    @Override
    public ResultModel<JSONObject> getYearBudget(Long userId, String year) {
        List<BudgetVO> budgetVOS=new ArrayList<>();
        List<Budget> budgets = budgetDAO.findYearById(userId, year+"%");
        com.alibaba.fastjson.JSONObject jsonObject=new com.alibaba.fastjson.JSONObject();
        BigDecimal expenditure;
        BigDecimal totalExpenditure=BigDecimal.ZERO;
        BigDecimal totalBudget=BigDecimal.ZERO;
        String month;
        for (Budget budget : budgets){
            expenditure=BigDecimal.ZERO;
            month=String.valueOf(budget.getBudgetDate().getMonth());
            if(month.length()<2){
                month="0"+month;
            }
            String monthDate= year+"-"+month+"%";
            if(expenditureDAO.sumByMonth(userId,monthDate)!=null){
                expenditure=expenditureDAO.sumByMonth(userId,monthDate);
            }
            totalExpenditure=totalExpenditure.add(expenditure);
            totalBudget=totalBudget.add(budget.getBudget());
            budgetVOS.add(BudgetVO.builder()
                    .date(String.valueOf(budget.getBudgetDate().getMonth()+1))
                    .balance(budget.getBudget().add(expenditure))
                    .budget(budget.getBudget())
                    .expenditure(expenditure)
                    .build());
        }
        jsonObject.put("totalBalance",totalBudget.add(totalExpenditure));
        jsonObject.put("totalBudget",totalBudget);
        jsonObject.put("totalExpenditure",totalExpenditure);
        jsonObject.put("budgetData",budgetVOS);
        return ResultModelUtil.success(jsonObject);
    }

    @Override
    public ResultModel<String> update(BudgetDTO budgetDTO) {
        String month=budgetDTO.getMonth();
        if(month.length()<2){
            month="0"+month;
        }
        String insertDate=budgetDTO.getYear()+"-"+month+"-01";
        String date= budgetDTO.getYear()+"-"+month+"%";
        System.out.println(date);
        //查看更新预算是否存在，不存在创建
        if (budgetDAO.findByIdAndBudgetDate(budgetDTO.getUserId(),date)==null){
            budgetDAO.insert(budgetDTO.getUserId(),insertDate,budgetDTO.getBudget());
            return ResultModelUtil.success("新增成功");
        }
        budgetDAO.update(budgetDTO.getUserId(),date,budgetDTO.getBudget());
        return ResultModelUtil.success("更新成功");
    }
}
