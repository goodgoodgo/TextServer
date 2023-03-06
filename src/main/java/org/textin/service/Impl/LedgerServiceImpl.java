package org.textin.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;
import org.textin.dao.*;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;
import org.textin.model.enums.ErrorCodeEn;
import org.textin.model.vo.IncomeVO;
import org.textin.model.vo.SubcategoryData;
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
import java.util.stream.Collectors;

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
        BigDecimal budget=BigDecimal.ZERO;
        if(null!=budgetDAO.findByIdAndBudgetDate(userID,date)){
            budget = budgetDAO.findByIdAndBudgetDate(userID,date).getBudget();
        }

        CheckUtil.isEmpty(ledgerDAO.get(userID), ErrorCodeEn.LEDGER_EMPTY);
        List<Income> incomes=incomeDAO.findById(id,userID,date);
        List<Expenditure> expenditures=expenditureDAO.findById(id,userID,date);
        incomes.addAll(TransferUtil.toIncome(expenditures));

        BigDecimal totalIncome=incomeSum(incomes,true);
        BigDecimal totalExpenditure=incomeSum(incomes,false);

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
    public String getChartInfo(Long userId,String data) {
        if(null!= UserHolder.getUser()){
            String email=UserHolder.getUser().getEmail();
            userId = userDAO.findUserByEmail(email).getId();
        }
        LocalDate localDate=LocalDate.now();
        String endDate = String.valueOf(localDate);
        String startDate;
        JSONObject jsonObject=new JSONObject();
        if(data.equals("day")){
            startDate= String.valueOf(localDate.minusDays(7));
            jsonObject=getJsonInfo(userId,startDate,endDate);
        }else if(data.equals("month")){
            startDate = String.valueOf(localDate.minusMonths(1));
            jsonObject=getJsonInfo(userId,startDate,endDate);
        }else if(data.equals("year")){
            startDate = String.valueOf(localDate.minusYears(1));
            jsonObject=getJsonInfo(userId,startDate,endDate);
            jsonObject.remove("incomeData");
            jsonObject.remove("expenditureData");
            List<IncomeVO> incomeYearVO=new ArrayList<>();
            List<IncomeVO> expenditureYearVO=new ArrayList<>();
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

        }else {
            startDate=data.split("to")[0];
            endDate=data.split("to")[1];
            getJsonInfo(userId,startDate,endDate);
        }

        //获取对应data内的数据 收入 支出 结余 预算
        return JSON.toJSONString(ResultModelUtil.success(jsonObject));
    }

    private BigDecimal incomeSum(List<Income> incomes,Boolean flag){
        BigDecimal totalIncome=BigDecimal.ZERO;
        BigDecimal totalExpenditure=BigDecimal.ZERO;
        for (Income income : incomes) {
            if (income.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                totalIncome = totalIncome.add(income.getAmount());
            } else {
                totalExpenditure = totalExpenditure.add(income.getAmount());
            }
        }
        if(flag==true){
            return totalIncome;
        }else {
            return totalExpenditure;
        }
    }

    private JSONObject getJsonInfo(Long userID,String startDate,String endDate){
        List<Income> incomes = incomeDAO.findBetweenDate(userID, startDate, endDate);
        List<Expenditure> expenditures=expenditureDAO.findBetweenDate(userID,startDate,endDate);
        List<Income> inAndExList=new ArrayList<>();
        inAndExList.addAll(incomes);
        inAndExList.addAll(TransferUtil.toIncome(expenditures));
        BigDecimal totalIncome=BigDecimal.ZERO;
        BigDecimal totalExpenditure=BigDecimal.ZERO;
        BigDecimal balance=BigDecimal.ZERO;
        for (Income income : inAndExList) {
            if (income.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                totalIncome = totalIncome.add(income.getAmount());
            } else {
                totalExpenditure = totalExpenditure.add(income.getAmount());
            }
        }
        //构造{日期,金额}数据结构，根据时间由早到晚进行排序
        Map<String,List<Income>> mergeMapByDate=dateSort((inAndExList));
        List<IncomeVO> incomeVOS=new ArrayList<>();
        List<IncomeVO> expenditureVOS=new ArrayList<>();
        for (String key : mergeMapByDate.keySet()) {
            IncomeVO incomeVO=new IncomeVO();
            IncomeVO expenditureVO=new IncomeVO();
            expenditureVO.setDate(key);
            expenditureVO.setAccount(BigDecimal.ZERO);
            if(null!= expenditureDAO.sumByDateAndUserId(userID,key)){
                expenditureVO.setAccount(expenditureDAO.sumByDateAndUserId(userID,key));
            }
            expenditureVOS.add(expenditureVO);
            incomeVO.setDate(key);
            incomeVO.setAccount(BigDecimal.ZERO);
            if(null!=incomeDAO.sumByDateAndUserId(userID,key)){
                incomeVO.setAccount(incomeDAO.sumByDateAndUserId(userID,key));
            }
            incomeVOS.add(incomeVO);
        }

        //构造{消费种类,金额，子类{消费子类,金额}}结构，根据金额由大到小排序
        Map<String,List<Income>> mergeMapByCategory=categorySort((inAndExList));
        List<IncomeVO> incomeCatVOS=new ArrayList<>();
        List<IncomeVO> expenditureCatVOS=new ArrayList<>();
        for (String key : mergeMapByCategory.keySet()) {
            List<SubcategoryData> subList=new ArrayList<>();
            BigDecimal bigDecimal=BigDecimal.ZERO;
            for (Income income : mergeMapByCategory.get(key)) {
                bigDecimal=bigDecimal.add(income.getAmount());
                subList.add(SubcategoryData.builder()
                        .account(income.getAmount())
                        .subcategory(income.getSubcategory())
                        .build());
            }
            if(bigDecimal.compareTo(BigDecimal.ZERO)<0){
                subList = subList.stream()
                        .sorted(Comparator.comparing(SubcategoryData::getAccount))
                        .collect(Collectors.toList());
                expenditureCatVOS.add(IncomeVO.builder()
                        .account(bigDecimal)
                        .category(key)
                        .subcategoryDate(subList)
                        .build());
            }else {
                subList = subList.stream()
                        .sorted(Comparator.comparing(SubcategoryData::getAccount,Collections.reverseOrder()))
                        .collect(Collectors.toList());
                incomeCatVOS.add(IncomeVO.builder()
                        .account(bigDecimal)
                        .category(key)
                        .subcategoryDate(subList)
                        .build());
            }
        }
        incomeCatVOS=incomeCatVOS.stream()
                .sorted(Comparator.comparing(IncomeVO::getAccount,Collections.reverseOrder()))
                .collect(Collectors.toList());
        expenditureCatVOS=expenditureCatVOS.stream()
                .sorted(Comparator.comparing(IncomeVO::getAccount))
                .collect(Collectors.toList());
        balance=totalIncome.add(totalExpenditure);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("totalIncome",totalIncome);
        jsonObject.put("totalExpenditure",totalExpenditure);
        jsonObject.put("balance",balance);
        jsonObject.put("incomeData",incomeVOS);
        jsonObject.put("expenditureData",expenditureVOS);
        jsonObject.put("incomeCatData",incomeCatVOS);
        jsonObject.put("expenditureCatData",expenditureCatVOS);
        return jsonObject;
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
