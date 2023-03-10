package org.textin.service.Impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;
import org.textin.dao.*;
import org.textin.model.dto.LedgerDTO;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;
import org.textin.model.entity.Ledger;
import org.textin.model.enums.ErrorCodeEn;
import org.textin.model.result.ResultModel;
import org.textin.model.vo.*;
import org.textin.service.LedgerService;
import org.textin.util.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
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
    public ResultModel<JSONArray> getLedger(Long id, String year, String month,Long userID) {
        userID=getUserId(userID);
        if(month.length()<2){
            month="0"+month;
        }
        String date=year+"-"+month+"%";
        BigDecimal budget=BigDecimal.ZERO;
        if(null!=budgetDAO.findYearById(userID,date)){
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
        Map<String,List<Income>> map=dateSort(incomes);
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
        return ResultModelUtil.success(jsonArray);
    }

    @Override
    public ResultModel<JSONObject> getChartInfo(Long userId, String data,String keyDate) {
        userId=getUserId(userId);
        LocalDate localDate=LocalDate.now();
        String endDate = String.valueOf(localDate);
        String startDate;
        JSONObject jsonObject=new JSONObject();
        if(data.equals("day")){
            startDate= String.valueOf(localDate.minusDays(6));
            jsonObject=getJsonInfo(userId,startDate,endDate,"day");
        }else if(data.equals("month")){
            YearMonth yearMonth = YearMonth.of(localDate.getYear(), Integer.valueOf(keyDate));
            if (keyDate.length()<2){
                keyDate="0"+keyDate;
            }
            startDate = localDate.getYear() + "-" +keyDate+ "-01";
            endDate = localDate.getYear()+"-"+keyDate+"-"+yearMonth.lengthOfMonth();
            jsonObject=getJsonInfo(userId,startDate,endDate,"month");
        }else if(data.equals("year")){
            startDate = keyDate+"-01-01";
            endDate=keyDate+"-12-31";
            jsonObject=getJsonInfo(userId,startDate,endDate,"year");
            jsonObject.remove("incomeData");
            jsonObject.remove("expenditureData");
            List<IncomeYearVO> incomeYearVO=new ArrayList<>();
            List<IncomeYearVO> expenditureYearVO=new ArrayList<>();
            String monthDate;
            for (int i = 1; i <= 12; i++) {
                if(1<=9){
                    monthDate=keyDate+"-0"+i+"%";
                }else {
                    monthDate=keyDate+"-"+i+"%";
                }
                BigDecimal bigDecimal=BigDecimal.ZERO;
                BigDecimal bigDecimalE=BigDecimal.ZERO;
                if(null!=incomeDAO.sumByMonth(userId,monthDate)){
                    bigDecimal=incomeDAO.sumByMonth(userId,monthDate);
                }
                if(null!=expenditureDAO.sumByMonth(userId,monthDate)){
                    bigDecimalE=expenditureDAO.sumByMonth(userId,monthDate);
                }
                incomeYearVO.add(IncomeYearVO.builder()
                        .date(String.valueOf(i))
                        .account(bigDecimal)
                        .build());
                expenditureYearVO.add(IncomeYearVO.builder()
                        .date(String.valueOf(i))
                        .account(bigDecimalE)
                        .build());
            }
            jsonObject.put("incomeData",incomeYearVO);
            jsonObject.put("expenditureData",expenditureYearVO);

        }else {
            startDate=data.split("to")[0];
            endDate=data.split("to")[1];
            if(Math.abs(Integer.valueOf(startDate.substring(0,4))-Integer.valueOf(endDate.substring(0,4)))>2){
                return ResultModelUtil.fail(401,"????????????");
            }
            jsonObject=getJsonInfo(userId,startDate,endDate,"def");
        }
        return ResultModelUtil.success(jsonObject);
    }

    @Override
    public ResultModel<JSONObject> getBill(Long userId,String year) {
        userId=getUserId(userId);
        List<BillVO> billVO=new ArrayList<>();
        String monthDate;
        BigDecimal totalIncome=BigDecimal.ZERO;
        BigDecimal totalExpenditure=BigDecimal.ZERO;
        BigDecimal bigDecimal;
        BigDecimal bigDecimalE;
        for (int i = 1; i <= 12; i++) {
            if(1<=9){
                monthDate=year+"-0"+i+"%";
            }else {
                monthDate=year+"-"+i+"%";
            }
            bigDecimal=BigDecimal.ZERO;
            bigDecimalE=BigDecimal.ZERO;
            if(null!=incomeDAO.sumByMonth(userId,monthDate)){
                bigDecimal=incomeDAO.sumByMonth(userId,monthDate);
            }
            if(null!=expenditureDAO.sumByMonth(userId,monthDate)){
                bigDecimalE=expenditureDAO.sumByMonth(userId,monthDate);
            }
            billVO.add(BillVO.builder()
                    .date(i+"???")
                    .income(bigDecimal.setScale( 0, BigDecimal.ROUND_UP ))
                    .expenditure(bigDecimalE.setScale( 0, BigDecimal.ROUND_UP ))
                    .balance(bigDecimal.add(bigDecimalE).setScale( 0, BigDecimal.ROUND_UP ))
                    .build());
        }
        for(BillVO billVO1 : billVO){
            totalIncome=totalIncome.add(billVO1.getIncome());
            totalExpenditure=totalExpenditure.add(billVO1.getExpenditure());
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("totalIncome",totalIncome);
        jsonObject.put("totalExpenditure",totalExpenditure);
        jsonObject.put("balance",totalIncome.add(totalExpenditure));
        jsonObject.put("monthData",billVO);
        return ResultModelUtil.success(jsonObject);
    }

    @Override
    public ResultModel<JSONObject> getMonthBill(Long userId, String year, String month) {
        userId=getUserId(userId);
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
        return ResultModelUtil.success(jsonObject);
    }

    @Override
    public ResultModel<List<LedgerVO>> getUserLedger(Long userId) {
        List<Ledger> ledgers = ledgerDAO.get(userId);
        List<LedgerVO> ledgerVOS=new ArrayList<>();
        ledgers.forEach(ledger ->{
            ledgerVOS.add(LedgerVO.builder()
                    .id(ledger.getId())
                    .name(ledger.getName())
                    .build());
        });
        return ResultModelUtil.success(ledgerVOS);
    }

    @Override
    public ResultModel<String> deleteLedger(Long userId, Long ledgerId) {
        ledgerDAO.delete(userId,ledgerId);
        return ResultModelUtil.success("????????????");
    }

    @Override
    public ResultModel<String> updateLedger(Ledger ledger) {
        ledgerDAO.update(ledger.getUserId(),ledger.getId(),ledger.getName());
        return ResultModelUtil.success("????????????");
    }

    @Override
    public ResultModel<String> saveLedger(LedgerDTO ledgerDTO) {
        Ledger ledger=Ledger.builder()
                .userId(ledgerDTO.getUserId())
                .name(ledgerDTO.getName())
                .build();
        ledger.setCreateAt(new Date());
        ledger.initBase();
        ledgerDAO.save(ledger);
        return ResultModelUtil.success("????????????");
    }

    private JSONObject getJsonInfo(Long userID,String startDate,String endDate,String flag){
        List<Income> incomes = incomeDAO.findBetweenDate(userID, startDate, endDate);
        List<Expenditure> expenditures=expenditureDAO.findBetweenDate(userID,startDate,endDate);
        List<Income> inAndExList=new ArrayList<>();
        inAndExList.addAll(incomes);
        inAndExList.addAll(TransferUtil.toIncome(expenditures));
        BigDecimal totalIncome=incomeSum(inAndExList,true);
        BigDecimal totalExpenditure=incomeSum(inAndExList,false);
        BigDecimal balance=BigDecimal.ZERO;
        //??????{??????,??????}???????????????????????????????????????????????????
        Map<String,List<Income>> mergeMapByDate=dateSort((inAndExList));
        List<IncomeChartVO> incomeVOS=new ArrayList<>();
        List<IncomeChartVO> expenditureVOS=new ArrayList<>();
        for (String key : mergeMapByDate.keySet()) {
            IncomeChartVO incomeVO=new IncomeChartVO();
            IncomeChartVO expenditureVO=new IncomeChartVO();
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
        LocalDate currentDateObj = LocalDate.parse(endDate);
        LocalDate startOfMonth;
        if(flag.equals("day")){
             startOfMonth = LocalDate.parse(startDate);
        }else {
             startOfMonth = LocalDate.parse(startDate.substring(0,7) + "-01");
        }
        List<IncomeChartVO> incomeResult = new ArrayList<>();
        List<IncomeChartVO> expenditureResult = new ArrayList<>();
        for (LocalDate date = startOfMonth; !date.isAfter(currentDateObj); date = date.plusDays(1)) {
            boolean foundIn = false;
            boolean foundEX = false;
            for (IncomeChartVO data : incomeVOS) {
                if (data.getDate().equals(date.toString())) {
                    incomeResult.add(data);
                    foundIn = true;
                    break;
                }
            }
            for (IncomeChartVO data : expenditureVOS) {
                if (data.getDate().equals(date.toString())) {
                    expenditureResult.add(data);
                    foundEX = true;
                    break;
                }
            }
            if (!foundEX) {
                expenditureResult.add(new IncomeChartVO(BigDecimal.ZERO, date.toString()));
            }
            if (!foundIn) {
                incomeResult.add(new IncomeChartVO(BigDecimal.ZERO, date.toString()));
            }
        }

        //??????{????????????,???????????????{????????????,??????}}???????????????????????????????????????
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
            subList=dateSortChart(subList);
            if(bigDecimal.compareTo(BigDecimal.ZERO)<0){
                subList = subList.stream()
                        .sorted(Comparator.comparing(SubcategoryData::getAccount))
                        .collect(Collectors.toList());
                expenditureCatVOS.add(IncomeVO.builder()
                        .account(bigDecimal)
                        .category(key)
                        .subcategoryData(subList)
                        .date("year")
                        .build());
            }else {
                subList = subList.stream()
                        .sorted(Comparator.comparing(SubcategoryData::getAccount,Collections.reverseOrder()))
                        .collect(Collectors.toList());
                incomeCatVOS.add(IncomeVO.builder()
                        .account(bigDecimal)
                        .category(key)
                        .subcategoryData(subList)
                        .date("year")
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
        jsonObject.put("incomeData",incomeResult);
        jsonObject.put("expenditureData",expenditureResult);
        jsonObject.put("incomeCatData",incomeCatVOS);
        jsonObject.put("expenditureCatData",expenditureCatVOS);
        return jsonObject;
    }

    private Long getUserId(Long userID){
        if(null!= UserHolder.getUser()){
            String email=UserHolder.getUser().getEmail();
            userID = userDAO.findUserByEmail(email).getId();
        }
        return userID;
    }

    /**
     * ???merge??????????????????????????????
     * @param incomes
     * @param flag
     * @return
     */
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

    /**
     * ??????????????????
     * @param incomes
     * @return
     */
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

    private List<SubcategoryData> dateSortChart(List<SubcategoryData> subcategoryData){
        Map<String, BigDecimal> accountBySubcategory = new HashMap<>();
        for (SubcategoryData data : subcategoryData) {
            String subcategory = data.getSubcategory();
            BigDecimal account = data.getAccount();
            if (accountBySubcategory.containsKey(subcategory)) {
                BigDecimal existingAccount = accountBySubcategory.get(subcategory);
                existingAccount=existingAccount.add(account);
                accountBySubcategory.put(subcategory,existingAccount );
            } else {
                accountBySubcategory.put(subcategory, account);
            }
        }

        // ????????????????????????????????????
        List<SubcategoryData> result = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : accountBySubcategory.entrySet()) {
            String subcategory = entry.getKey();
           BigDecimal account = entry.getValue();
            result.add(new SubcategoryData(account, subcategory));
        }
        return result;
    }


    /**
     * ??????????????????
     * @param incomes
     * @return
     */
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