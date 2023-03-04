package org.textin.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;
import org.textin.dao.*;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;
import org.textin.model.enums.ErrorCodeEn;
import org.textin.service.LedgerService;
import org.textin.util.CheckUtil;
import org.textin.util.ResultModelUtil;
import org.textin.util.TransferUtil;
import org.textin.util.UserHolder;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:50
 */
@Service
public class LedgerServiceImpl implements LedgerService {


    @Resource
    private UserDAO userDAO;

    @Resource
    private IncomeDAO incomeDAO;

    @Resource
    private ExpenditureDAO expenditureDAO;

    @Resource
    private LedgerDAO ledgerDAO;

    @Resource
    private BudgetDAO budgetDAO;
    @Override
    public String getLedger(Long id, String year, String month,Long userID) {
        if(null!= UserHolder.getUser()){
            String email=UserHolder.getUser().getEmail();
            userID = userDAO.findUserByEmail(email).getId();
        }
        if(month.length()<2){
            month="0"+month;
        }
        String date=year+"-"+month+"%";
        BigDecimal budget;
        if(null!=budgetDAO.findByIdAndBudgetDate(userID,date)){
            budget = budgetDAO.findByIdAndBudgetDate(userID,date).getBudget();
        }else {
            budget=BigDecimal.ZERO;
        }

        CheckUtil.isEmpty(ledgerDAO.get(userID), ErrorCodeEn.LEDGER_EMPTY);
        List<Income> incomes=incomeDAO.findById(id,userID,date);
        List<Expenditure> expenditures=expenditureDAO.findById(id,userID,date);
        incomes.addAll(TransferUtil.toIncome(expenditures));

        BigDecimal totalIncome=BigDecimal.ZERO;
        BigDecimal totalExpenditure=BigDecimal.ZERO;
        for (Income income : incomes) {
            if (income.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                totalIncome = totalIncome.add(income.getAmount());
            } else {
                totalExpenditure = totalExpenditure.add(income.getAmount());
            }
        }
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
            if(null!= expenditureDAO.sumByDateAndId(id,userID,key)){
                expenditureSum.add(expenditureDAO.sumByDateAndId(id,userID,key));
            }
            else {
                expenditureSum.add(0.00);
            }
            if(null!=incomeDAO.sumByDateAndId(id,userID,key)){
                incomeSum.add(incomeDAO.sumByDateAndId(id,userID,key));
            }
            else {
                incomeSum.add(0.00);
            }
            week.add(LocalDate.parse(key)
                    .getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINA));
            dayDate.add(key);
        }

        JSONArray jsonArray=new JSONArray();
        JSONObject topObject = new JSONObject();
        topObject.put("totalIncome",totalIncome);
        topObject.put("totalExpenditure",totalExpenditure);
        topObject.put("budget",budget);
        jsonArray.add(topObject);
        List<List<Income>> lists=new ArrayList<>(reversedTreeMap.values());
        for (int i = 0; i < lists.size(); i++) {
            Integer num=lists.size()-i-1;
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("dayIncome",incomeSum.get(num));
            jsonObject.put("dayExpenditure",expenditureSum.get(num));
            jsonObject.put("date",dayDate.get(num));
            jsonObject.put("week",week.get(num));
            jsonObject.put("dayData",lists.get(i));
            jsonArray.add(jsonObject);
        }
        return JSON.toJSONString(ResultModelUtil.success(jsonArray));
    }

    @Override
    public String getChartInfo(String data) {
        //判断请求date类型(day month year 2021-03-08to2022-04-08)
        if(data.equals("day")){

        }else if(data.equals("month")){

        }else if(data.equals("year")){

        }else {

        }
        //获取对应data内的数据 收入 支出 结余 预算
        return null;
    }
}
