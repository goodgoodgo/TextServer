package org.textin;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.textin.dao.ExpenditureDAO;
import org.textin.dao.IncomeDAO;
import org.textin.dao.UserDAO;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;
import org.textin.model.vo.*;
import org.textin.service.BudgetService;
import org.textin.util.ResultModelUtil;
import org.textin.util.TransferUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:25
 */
@SpringBootTest
public class STest {

    @Resource
    private IncomeDAO incomeDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private ExpenditureDAO expenditureDAO;
    @Resource
    private BudgetService budgetService;
    @Test
    void testString(){
        Long userId=1l;
        String year="2022";
        String month="1";
        if(month.length()<2){
            month="0"+month;
        }
        String date=year+"-"+month+"%";
        BigDecimal totalIncome=BigDecimal.ZERO;
        BigDecimal totalExpenditure=BigDecimal.ZERO;
        if(null!=incomeDAO.sumByMonth(userId,date)){
            totalIncome=incomeDAO.sumByMonth(userId,date);
        }
        if(null!=expenditureDAO.sumByMonth(userId,date)){
            totalExpenditure=expenditureDAO.sumByMonth(userId,date);
        }
        List<Expenditure> expenditures=expenditureDAO.findByIdAndMonth(userId,date);
        Comparator<Expenditure> expendituresComparator = Comparator.comparing(Expenditure::getAmount);
        expenditures.sort(expendituresComparator);
        JSONObject jsonObject=new JSONObject();
        List<BillDayVO> billDayVOS=new ArrayList<>();
        expenditures.forEach((expenditure -> {
            billDayVOS.add(BillDayVO.builder()
                    .date(expenditure.getExpenditureDate())
                    .category(expenditure.getCategory())
                    .account(expenditure.getAmount())
                    .build());
        }));
        jsonObject.put("totalIncome",totalIncome);
        jsonObject.put("totalExpenditure",totalExpenditure);
        jsonObject.put("dayData",billDayVOS);
        System.out.println(jsonObject);
    }

    private Map<String,List<Income>> dateSort(List<Income> incomes){
        Map<String,List<Income>> map=new TreeMap<>();
        incomes.forEach(income -> {
            List<Income> incomeList;
            if(map.containsKey(income.getIncomeDate())){
                incomeList = map.get(income.getIncomeDate());
            }else {
                incomeList = new ArrayList<>();
            }
            incomeList.add(income);
            map.put(income.getIncomeDate(),incomeList);
        });
        return map;
    }

    public static void main(String[] args) {
        BigDecimal bigDecimal=new BigDecimal(30);
        System.out.println(bigDecimal.divide(BigDecimal.valueOf(30)));
    }
}