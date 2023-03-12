package org.textin.service;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.textin.model.dto.LedgerDTO;
import org.textin.model.entity.Ledger;
import org.textin.model.result.ResultModel;
import org.textin.model.vo.LedgerVO;

import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:36
 */
public interface LedgerService {
    /**
     * 第一个主页面
     * @param id
     * @param year
     * @param month
     * @param userId
     * @return
     */
    ResultModel<JSONArray> getLedger(Long id, String year, String month, Long userId);

    /**
     * 图表页
     * @param userId
     * @param data
     * @param key
     * @return
     */
    ResultModel<JSONObject> getChartInfo(Long userId, String data,String key);

    /**
     * 账单页
     * @param userId
     * @param year
     * @return
     */
    ResultModel<JSONObject> getBill(Long userId,String year);

    /**
     * 月账单页
     * @param userId
     * @param year
     * @param month
     * @return
     */
    ResultModel<JSONObject> getMonthBill(Long userId, String year, String month);
    ResultModel<List<LedgerVO>> getUserLedger(Long userId);
    ResultModel<String> deleteLedger(Long userId, Long ledgerId);
    ResultModel<String> updateLedger(Ledger ledger);
    ResultModel<String> saveLedger(LedgerDTO ledgerDTO);
}
