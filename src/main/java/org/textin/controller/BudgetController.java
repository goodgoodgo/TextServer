package org.textin.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Result;
import org.springframework.web.bind.annotation.*;
import org.textin.model.dto.BudgetDTO;
import org.textin.model.result.ResultModel;
import org.textin.service.BudgetService;

import javax.annotation.Resource;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-11 10:02
 */
@RestController
@RequestMapping("/budget")
public class BudgetController {
    @Resource
    private BudgetService budgetService;

    @GetMapping
    public ResultModel<JSONObject> getBudget(@RequestParam("userId") Long userId,@RequestParam("year") String year){
        return budgetService.getYearBudget(userId,year);
    }

    @PostMapping("/update")
    public ResultModel<String> updateMonthBudget(@RequestBody BudgetDTO budgetDTO){
        return budgetService.update(budgetDTO);
    }
}
