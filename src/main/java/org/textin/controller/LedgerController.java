package org.textin.controller;

import org.springframework.web.bind.annotation.*;
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
    public String getLedgerPage(@RequestParam("ledgerId") Long ledgerId,@RequestParam("year") String year,
                                @RequestParam("month") String mouth,@RequestParam("userId") Long userId){
        return ledgerService.getLedger(ledgerId,year,mouth,userId);
    }

    @GetMapping("/chart")
    public String getChartPage(@RequestParam("userId") Long userId,@RequestParam("date") String data){
        return ledgerService.getChartInfo(userId,data);
    }
}
