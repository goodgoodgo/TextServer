package org.textin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.textin.model.result.ResultModel;
import org.textin.service.LedgerService;

import javax.annotation.Resource;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 08:23
 */
@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Resource
    private LedgerService ledgerService;
    @GetMapping
    public ResultModel<JSONArray> getLedgerPage(@RequestParam("ledgerId") Long ledgerId, @RequestParam("year") String year,
                                                @RequestParam("month") String mouth, @RequestParam("userId") Long userId){
        return ledgerService.getLedger(ledgerId,year,mouth,userId);
    }

    @GetMapping("/chart")
    public ResultModel<JSONObject> getChartPage(@RequestParam("userId") Long userId, @RequestParam("date") String data){
        return ledgerService.getChartInfo(userId,data);
    }

    @GetMapping("/bill")
    public ResultModel<JSONObject> getYearBillPage(@RequestParam("userId") Long userId,@RequestParam("year") String year){
        return ledgerService.getBill(userId,year);
    }

    @GetMapping("/bill/monthBill")
    private ResultModel<JSONObject> getMonthBillPage(@RequestParam("userId") Long userId,
                                                     @RequestParam("year") String year,@RequestParam("month") String month){
        return ledgerService.getMonthBill(userId,year,month);
    }
}
