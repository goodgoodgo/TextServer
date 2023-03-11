package org.textin.service.Impl;

import org.springframework.stereotype.Service;
import org.textin.dao.ExpenditureDAO;
import org.textin.model.entity.Expenditure;
import org.textin.model.result.ResultModel;
import org.textin.service.ExpenditureService;
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
 * @create: 2022-12-23 18:44
 */

@Service
public class ExpenditureServiceImpl implements ExpenditureService {

    @Resource
    ExpenditureDAO expenditureDAO;

    @Override
    public ResultModel<String> save(Expenditure expenditure) {
        if(expenditure.getSubcategory()==null||expenditure.getSubcategory().length()==0){
            expenditure.setSubcategory(defaultSub(expenditure.getCategory()));
        }
        expenditure.setCreateAt(new Date());
        expenditure.initBase();
        EventBus.emit(EventBus.MsgType.EXPENDITURE_CREATE,expenditure);
        expenditureDAO.insert(expenditure);
        return ResultModelUtil.success("添加成功");
    }

    @Override
    public ResultModel<String> delete(Long id) {
        expenditureDAO.deleteById(id);
        return ResultModelUtil.success("删除成功");
    }

    @Override
    public ResultModel<String> update(Expenditure expenditure) {
        if(expenditure.getId()==null){
            ResultModelUtil.fail(9900,"id为空");
        }
        if(expenditure.getAmount()==null){
            expenditure.setAmount(BigDecimal.ZERO);
        }
        if (expenditure.getExpenditureDate()==null){
            expenditure.setExpenditureDate(String.valueOf(LocalDate.now()));
        }
        if(expenditure.getComment()==null){
            expenditure.setComment("");
        }
        EventBus.emit(EventBus.MsgType.EXPENDITURE_UPDATE,expenditure);
        expenditureDAO.updateExpenditure(expenditure);
        return ResultModelUtil.success("更新成功");
    }
    
    private String defaultSub(String category){
        String s="";
        if(category.equals("食品餐饮")){
            s="生鲜食品";
        }
        if(category.equals("购物消费")){
            s="日用家居 ";
        }
        if(category.equals("出行交通")){
            s="停车过路";
        }
        if(category.equals("休闲娱乐")){
            s="旅游度假";
        }
        if(category.equals("服务缴费")){
            s="水电燃气";
        }
        if(category.equals("学习教育")){
            s="学费支出";
        }
        if(category.equals("人情往来")){
            s="礼物红包";
        }
        if(category.equals("健康医疗")){
            s="医院诊疗";
        }
        if(category.equals("金融理财")){
            s="理财投资";
        }
        return s;
    }
}
