package org.textin.service.Impl;

import org.springframework.stereotype.Component;
import org.textin.dal.dao.IncomeDao;
import org.textin.dal.dataobject.IncomeDO;
import org.textin.model.entity.Income;
import org.textin.model.result.ResultModel;
import org.textin.model.transfer.IncomeTransfer;
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
@Component
public class IncomeServiceImpl implements IncomeService {


    @Resource
    private IncomeDao incomeDao;


    @Override
    public ResultModel save(Income income) {
        IncomeDO incomeDO= IncomeTransfer.toIncomeDO(income);
        incomeDO.initBase();
        incomeDO.setCreateAt(new Date());
        incomeDao.insert(incomeDO);
        EventBus.emit(EventBus.MsgType.INCOME_CREATE,income);
        return ResultModelUtil.success();
    }

    @Override
    public ResultModel<Boolean> delete(Income income) {
        return null;
    }

    @Override
    public ResultModel<String> findIncomeByDate(Date date) {
        return null;
    }
}
