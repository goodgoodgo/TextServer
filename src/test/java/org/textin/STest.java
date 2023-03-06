package org.textin;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.textin.dao.ExpenditureDAO;
import org.textin.dao.IncomeDAO;
import org.textin.dao.UserDAO;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;
import org.textin.model.vo.IncomeVO;
import org.textin.model.vo.SubcategoryData;
import org.textin.service.BudgetService;
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
        LocalDate localDate=LocalDate.now();
        List<IncomeVO> incomeYearVO=new ArrayList<>();
        List<IncomeVO> expenditureYearVO=new ArrayList<>();
        JSONObject jsonObject=new JSONObject();
        String monthDate;
        for (int i = 1; i <= 12; i++) {
            if(1<=9){
               monthDate=localDate.getYear()+"-0"+i+"%";
            }else {
                monthDate=localDate.getYear()+"-"+i+"%";
            }
            BigDecimal bigDecimal=BigDecimal.ZERO;
            BigDecimal bigDecimalE=BigDecimal.ZERO;
            if(null!=incomeDAO.sumByMonth(userId,monthDate)){
                bigDecimal=incomeDAO.sumByMonth(userId,monthDate);
            }
            if(null!=expenditureDAO.sumByMonth(userId,monthDate)){
                bigDecimalE=expenditureDAO.sumByMonth(userId,monthDate);
            }
            incomeYearVO.add(IncomeVO.builder()
                    .date(String.valueOf(i))
                    .account(bigDecimal)
                    .build());
            expenditureYearVO.add(IncomeVO.builder()
                    .date(String.valueOf(i))
                    .account(bigDecimalE)
                    .build());
        }
        jsonObject.put("incomeData",incomeYearVO);
        jsonObject.put("expenditureData",expenditureYearVO);
        System.out.println(jsonObject);
    }

    @Test
    void test(){

    }

    public static void main(String[] args) {
        BigDecimal bigDecimal=new BigDecimal(30);
        System.out.println(bigDecimal.divide(BigDecimal.valueOf(30)));
    }
}