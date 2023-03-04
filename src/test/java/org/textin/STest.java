package org.textin;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.textin.dao.ExpenditureDAO;
import org.textin.dao.IncomeDAO;
import org.textin.dao.UserDAO;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;
import org.textin.util.TransferUtil;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;



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
    @Test
    void testString(){
        Integer year=2022;
        Integer month=7;
        List<Expenditure> byId = expenditureDAO.findById(1L, 1L,"2022-07%");
        List<Income> incomes = incomeDAO.findById(1l, 1l,"2022-07%");
        incomes.addAll(TransferUtil.toIncome(byId));

        Comparator<Income> incomeComparator = Comparator.comparing(Income::getCreateAt).reversed();
        incomes.sort(incomeComparator);
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
        Map<String,List<Income>> reversedTreeMap = new TreeMap<>(Collections.reverseOrder());
        reversedTreeMap.putAll(map);
        JSONArray jsonArray=new JSONArray();
        List<List<Income>> lists=new ArrayList<>(reversedTreeMap.values());
        lists.forEach((list)->{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("dayIncome",100.00);
            jsonObject.put("dayExpenditure",100.00);
            jsonObject.put("date","2022-3-03");
            jsonObject.put("week","星期一");
            jsonObject.put("dayData",list);
            jsonArray.add(jsonObject);
        });
        System.out.println(jsonArray);
    }

    @Test
    void test(){
        List<Expenditure> byId = expenditureDAO.findById(1L, 1L,"2022-07%");
        List<Income> incomes = incomeDAO.findById(1l, 1l,"2022-07%");
        incomes.addAll(TransferUtil.toIncome(byId));

        Comparator<Income> incomeComparator = Comparator.comparing(Income::getCreateAt).reversed();
        incomes.sort(incomeComparator);
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
        Map<String,List<Income>> reversedTreeMap = new TreeMap<>(Collections.reverseOrder());
        reversedTreeMap.putAll(map);
        List<Double> incomeSum=new ArrayList<>();
        List<Double> expenditureSum=new ArrayList<>();
        List<String> week=new ArrayList<>();
        List<String> dayDate=new ArrayList<>();
        for (String key : map.keySet()) {
            if(null!= expenditureDAO.sumByDateAndId(1l,1l,key)){
                expenditureSum.add(expenditureDAO.sumByDateAndId(1l,1l,key));
            }
            if(null!=incomeDAO.sumByDateAndId(1l,1l,key)){
                incomeSum.add(incomeDAO.sumByDateAndId(1l,1l,key));
            }
            week.add(LocalDate.parse(key)
                    .getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINA));
            dayDate.add(key);
        }
        System.out.println(incomeSum);
        System.out.println(expenditureSum);
        System.out.println(week);
        System.out.println(dayDate);
    }
}