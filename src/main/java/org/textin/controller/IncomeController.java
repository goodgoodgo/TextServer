package org.textin.controller;

import org.springframework.web.bind.annotation.*;
import org.textin.model.entity.Income;
import org.textin.model.result.ResultModel;
import org.textin.service.IncomeService;

import javax.annotation.Resource;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-25 09:53
 */
@RestController
@RequestMapping("/income")
public class IncomeController {

    @Resource
    private IncomeService incomeService;

    @PostMapping("/save")
    public ResultModel<String> saveIncome(@RequestBody Income income){
        return incomeService.save(income);
    }

    @PostMapping("/delete")
    public ResultModel<String> deleteIncome(@RequestParam("id") Long id){
        return incomeService.delete(id);
    }

    @PostMapping("/update")
    public ResultModel<String> updateIncome(@RequestBody Income income){
        return incomeService.update(income);
    }
}
