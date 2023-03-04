package org.textin.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.textin.annotation.IsLogin;
import org.textin.dao.IncomeDAO;
import org.textin.model.entity.Income;
import org.textin.model.result.ResultModel;
import org.textin.service.IncomeService;
import org.textin.util.EventBus;
import org.textin.util.ResultModelUtil;

import javax.annotation.Resource;
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


    @IsLogin
    @Override
    public String save(Income income) {
        income.setCreateAt(new Date());
        income.initBase();
        incomeDao.insert(income);
        EventBus.emit(EventBus.MsgType.INCOME_CREATE,income);
        return JSON.toJSONString(ResultModelUtil.success());
    }

    @IsLogin
    @Override
    public ResultModel<Boolean> delete(Income income) {
        return null;
    }

    @Override
    public ResultModel<String> findIncomeByDate(Date date) {
        return null;
    }
}
