package org.textin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.textin.model.result.ResultModel;
import org.textin.service.ExpenditureService;
import org.textin.util.JsonMsgUtil;
import org.textin.util.ResultModelUtil;
import org.textin.util.SocketUtil;
import org.textin.util.TextHttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2022-12-23 18:44
 */

@Service
public class ExpenditureServiceImpl implements ExpenditureService {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public HashMap<String, String> KeepAccountByImageShop(byte[] imgBytes) {

        HashMap<String, String> map = new HashMap<>();
        List<String> textList = new ArrayList<>();
        String urlCommon = "https://api.textin.com/ai/service/v2/recognize";

        JSONObject jsonObject = JSON.parseObject(TextHttpUtil.postUrl(urlCommon, imgBytes))
                .getJSONObject("result");


        jsonObject.getJSONArray("lines").stream().forEach(line -> {
            textList.add(JSON.parseObject(line + "").getString("text"));
        });

        String shopText = "";
        shopText = JSON.parseObject(TextHttpUtil.postUrl("https://api.textin.com/robot/v1.0/api/receipt", imgBytes))
                .getJSONObject("result")
                .getJSONArray("item_list")
                .getJSONObject(5)
                .getString("value");
        String[] lines = shopText.split("\n");
        if (lines.length >= 2) {
            shopText = lines[lines.length / 2];
        } else if (lines.length == 1) {
            shopText = lines[0];
        }

        String time = JsonMsgUtil.getTime(textList);
        String price = JsonMsgUtil.getPrice(textList);
        String type = SocketUtil.remoteCall(shopText);

        if (time.length() < 10) {
            Date currentDate = new Date();
            String currentDateString = dateFormat.format(currentDate);
            time = currentDateString + " " + time;
        }

        map.put("time", time);
        map.put("price", price);
        map.put("type", type);

        return map;
    }

    @Override
    public HashMap<String, String> KeepAccountByImageTicket(byte[] imgBytes) {

        HashMap<String, String> map = new HashMap<>();
        String url = "https://api.textin.com/robot/v1.0/api/train_ticket";
        JSONObject jsonObject = JSON.parseObject(JSON.parseObject(TextHttpUtil.postUrl(url, imgBytes))
                .getString("result"));
        String type = jsonObject.getString("type_description");
        String price = JSON.parseObject(jsonObject.getJSONArray("item_list").getString(7))
                .getString("value");
        String time = JSON.parseObject(jsonObject.getJSONArray("item_list").getString(5))
                .getString("value");
        System.out.println(type + "  " + price + "  " + time);
        map.put("time", time);
        map.put("price", price);
        map.put("type", type);
        return map;
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
