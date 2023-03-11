package org.textin.service.Impl;

import org.springframework.stereotype.Service;
import org.textin.dao.IncomeDAO;
import org.textin.model.entity.Income;
import org.textin.model.result.ResultModel;
import org.textin.service.IncomeService;
import org.textin.util.EventBus;
import org.textin.util.ResultModelUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 10:42
 */
@Service
public class IncomeServiceImpl implements IncomeService {


    @Resource
    private IncomeDAO incomeDao;


    @Override
    public ResultModel<String> save(Income income) {
        income.setCreateAt(new Date());
        income.initBase();
        incomeDao.insert(income);
        EventBus.emit(EventBus.MsgType.INCOME_CREATE,income);
        return ResultModelUtil.success();
    }


    @Override
    public ResultModel<String> delete(Long id) {
        incomeDao.deleteById(id);
        return ResultModelUtil.success("删除成功");
    }

    @Override
    public ResultModel<String> update(Income income) {
        if(income.getId()==null){
            ResultModelUtil.fail(9900,"id为空");
        }
        if(income.getAmount()==null){
            income.setAmount(BigDecimal.ZERO);
        }
        if (income.getIncomeDate()==null){
            income.setIncomeDate(String.valueOf(LocalDate.now()));
        }
        if(income.getComment()==null){
            income.setComment("");
        }
        EventBus.emit(EventBus.MsgType.INCOME_UPDATE,income);
        incomeDao.updateIncome(income);
        return ResultModelUtil.success("更新成功");
    }

}
