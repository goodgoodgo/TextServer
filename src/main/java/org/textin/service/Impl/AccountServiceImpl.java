package org.textin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.textin.service.AccountService;
import org.textin.util.SocketUtil;
import org.textin.util.TextHttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-10 15:38
 */
@Service
public class AccountServiceImpl implements AccountService {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public JSONObject KeepAccountByImageShop(byte[] imgBytes) {

        String urlCommon = "https://api.textin.com/ai/service/v2/recognize";
        JSONObject result=new JSONObject();
        String mostFrequentType = null;
        String shopText = "";
        JSONObject jsonObject= JSON.parseObject(TextHttpUtil.postUrl("https://api.textin.com/robot/v1.0/api/receipt", imgBytes));
        JSONArray jsonArray=jsonObject
                .getJSONObject("result")
                .getJSONArray("item_list");
        shopText = jsonObject
                .getJSONObject("result")
                .getJSONArray("item_list")
                .getJSONObject(5)
                .getString("value");
        List<String> typeList=new ArrayList<>();

        if(!shopText.equals("")){
            for (String commodity : shopText.split("\n")){
                typeList.add(SocketUtil.remoteCall(commodity));
            }
            Map<String, Integer> typeCounts = new HashMap<>();
            for (String name : typeList) {
                typeCounts.put(name, typeCounts.getOrDefault(name,0)+1);
            }

            int maxCount = 0;
            for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
                if (entry.getValue() > maxCount || (entry.getValue() == maxCount && entry.getKey().equals(mostFrequentType))) {
                    mostFrequentType = entry.getKey();
                    maxCount = entry.getValue();
                }
            }
        }else {
            mostFrequentType="食品餐饮1";
        }
        result.put("category",mostFrequentType.split("、")[0]);
        if(mostFrequentType.split("、").length>=2){
            result.put("subcategory",mostFrequentType.split("、")[1]);
        }
        else {
            result.put("subcategory","");
        }
        result.put("amount",jsonArray.getJSONObject(0).getString("value"));
        result.put("comment",jsonArray.getJSONObject(3).getString("value"));
        result.put("expenditureDate",jsonArray.getJSONObject(1).getString("value").split(" ")[0]);
        return result;
    }

    @Override
    public JSONObject KeepAccountByImageTicket(byte[] imgBytes) {

        JSONObject result=new JSONObject();
        String url = "https://api.textin.com/robot/v1.0/api/train_ticket";
        JSONObject jsonObject = JSON.parseObject(JSON.parseObject(TextHttpUtil.postUrl(url, imgBytes))
                .getString("result"));
        String price = JSON.parseObject(jsonObject.getJSONArray("item_list").getString(7))
                .getString("value");
        String time = JSON.parseObject(jsonObject.getJSONArray("item_list").getString(5))
                .getString("value");
        result.put("expenditureDate", time);
        result.put("amount", "-"+price);
        result.put("category", "交通出行");
        return result;
    }

    @Override
    public String typeServer(byte[] imgBytes) {

        String url = "https://api.textin.com/robot/v1.0/api/general_receipt_classify";
        String type = "";
        type = JSON.parseObject(JSON.parseObject(TextHttpUtil.postUrl(url, imgBytes))
                        .getString("result"))
                .getString("type");
        return type;
    }
}
