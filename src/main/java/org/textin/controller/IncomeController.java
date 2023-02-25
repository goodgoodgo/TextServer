package org.textin.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;
import org.textin.model.entity.Income;
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
    public String saveIncome(@RequestBody Income income){
        return JSON.toJSONString(incomeService.save(income));
    }
}
