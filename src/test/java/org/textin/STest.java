package org.textin;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
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
import org.textin.util.SocketUtil;
import org.textin.util.TextHttpUtil;
import org.textin.util.TransferUtil;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
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
    void testString() throws FileNotFoundException {
        
    }

    public static void main(String[] args) throws FileNotFoundException {
        String currentMonth = "2023-03"; // 当前月份
        String endDate = "2023-03-31"; // 当前日期
        LocalDate currentDateObj = LocalDate.parse(endDate);
        LocalDate startOfMonth = LocalDate.parse(currentMonth + "-01");
        List<IncomeChartVO> incomeData = new ArrayList<>();

        List<IncomeChartVO> result = new ArrayList<>();
        for (LocalDate date = startOfMonth; !date.isAfter(currentDateObj); date = date.plusDays(1)) {
            boolean found = false;
            for (IncomeChartVO data : incomeData) {
                if (data.getDate().equals(date.toString())) {
                    result.add(data);
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(new IncomeChartVO(BigDecimal.ZERO, date.toString()));
            }
        }
        System.out.println(JSON.toJSONString(result));
    }

    private Map<String,List<Income>> categorySort(List<Income> incomes){
        Map<String,List<Income>> map=new TreeMap<>();
        incomes.forEach(income -> {
            List<Income> incomeList;
            if(map.containsKey(income.getCategory())){
                incomeList = map.get(income.getCategory());
            }else {
                incomeList = new ArrayList<>();
            }
            incomeList.add(income);
            map.put(income.getCategory(),incomeList);
        });
        return map;
    }
}