package org.textin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import org.textin.model.dto.LedgerDTO;
import org.textin.model.entity.Ledger;
import org.textin.model.result.ResultModel;
import org.textin.model.vo.LedgerVO;
import org.textin.service.LedgerService;

import javax.annotation.Resource;
import java.util.List;

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
    public ResultModel<JSONObject> getChartPage(@RequestParam("userId") Long userId, @RequestParam("date") String data,
                                                @RequestParam("key") String keyDate){
        return ledgerService.getChartInfo(userId,data,keyDate);
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

    @GetMapping("userLedgers")
    private ResultModel<List<LedgerVO>> getUserLedger(@RequestParam("userId") Long userId){
        return ledgerService.getUserLedger(userId);
    }

    @PostMapping("delete")
    private ResultModel<String> deleteLedger(@RequestParam("userId") Long userId,@RequestParam("ledgerId") Long ledgerId){
        return ledgerService.deleteLedger(userId,ledgerId);
    }
    @PostMapping("update")
    private ResultModel<String> updateLedger(@RequestBody Ledger ledger){
        return ledgerService.updateLedger(ledger);
    }

    @PostMapping("save")
    private ResultModel<String> saveLedger(@RequestBody LedgerDTO ledgerDTO){
        return ledgerService.saveLedger(ledgerDTO);
    }
}
