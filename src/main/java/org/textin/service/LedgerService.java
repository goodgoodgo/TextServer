package org.textin.service;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.textin.model.result.ResultModel;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:36
 */
public interface LedgerService {
    ResultModel<JSONArray> getLedger(Long id, String year, String month, Long userId);
    ResultModel<JSONObject> getChartInfo(Long userId, String data,String key);
    ResultModel<JSONObject> getBill(Long userId,String year);
    ResultModel<JSONObject> getMonthBill(Long userId, String year, String month);
}
