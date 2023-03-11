package org.textin.controller;


import org.springframework.web.bind.annotation.*;
import org.textin.model.entity.Expenditure;
import org.textin.model.result.ResultModel;
import org.textin.service.ExpenditureService;


import javax.annotation.Resource;

/**
*@program: ServerTest
*@description:
*@author: ma
*@create: 2022-12-10 15:51
*/
@RestController
@RequestMapping("/expenditure")
public class ExpenditureController {
    @Resource
    private ExpenditureService expenditureService;

    @PostMapping("/save")
    public ResultModel<String> saveExpenditure(@RequestBody Expenditure expenditure){
        return expenditureService.save(expenditure);
    }

    @PostMapping("/delete")
    public ResultModel<String> deleteExpenditure(@RequestParam("id") Long id){
        return expenditureService.delete(id);
    }

    @PostMapping("/update")
    public ResultModel<String> updateExpenditure(@RequestBody Expenditure expenditure){
        return expenditureService.update(expenditure);
    }
}
