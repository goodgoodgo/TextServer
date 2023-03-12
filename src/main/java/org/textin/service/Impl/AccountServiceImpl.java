package org.textin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.springframework.stereotype.Service;
import org.textin.model.result.ResultModel;
import org.textin.service.AccountService;
import org.textin.util.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public ResultModel<JSONObject> keepAccountByWAV(byte[] bytes) {
        String text="";
        try {
            text= RecognitionUtil.speechRecognition(bytes);
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
        text = text.replace("两", "二");
        text= ChineseNumberConverterUtil.ChineseNumberReplace(text);
        String name="";
        List<Term> termList = HanLP.segment(text);
        String month = "";
        String day = "";
        String matchValue="";
        String date="";
        Double matchedValue=0.00;
        List<String> valueList = new ArrayList<>();
        String regex = "(\\d+块\\d+毛\\d+|\\d+块\\d+毛|\\d+元\\d+角\\d+分|\\d+元\\d+角|\\d+元|\\d+块)(\\d+毛\\d+|\\d+毛|\\d+分)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matchValue = matcher.group();
            matchedValue = convertToDouble(matchValue);
            break;
        }
        for (int i = 0; i < termList.size() - 1; i++) {
            Term currentTerm = termList.get(i);
            Term nextTerm = termList.get(i + 1);
            if (currentTerm.nature.startsWith("m") && nextTerm.nature.startsWith("q")) {
                String qValue = nextTerm.word;
                if (qValue.equals("月")) {
                    month = String.valueOf(currentTerm.word);
                } else if (qValue.equals("号")||qValue.equals("日")) {
                    day = String.valueOf(currentTerm.word);
                }
            }
        }
        String lastM="";
        for (Term term : termList) {
            if (String.valueOf(term.nature).equals("m")){
                lastM=term.word;
            }
        }
        if(!lastM.equals("")){
            valueList.add(lastM);
        }
        if(matchValue.equals("")){
            matchValue=lastM;
        }
        for (Term term : termList) {
            if (String.valueOf(term.nature).equals("n")){
                name=term.word;
            }
        }
        for (Term term : termList) {
            if (String.valueOf(term.nature).equals("v")&&name.equals("")){
                name=term.word;
                break;
            }
        }
        if(text.contains("今天")){
            date= LocalDate.now().toString();
        }else if(text.contains("昨天")){
            date=LocalDate.now().minusDays(1).toString();
        }else if(text.contains("前天")){
            date=LocalDate.now().minusDays(2).toString();
        }else if(!month.equals("")&&!day.equals("")){
            if(day.length()<2){
                day="0"+day;
            }
            if (month.length()<2){
                month="0"+month;
            }
            date=LocalDate.now().getYear()+"-"+month+"-"+day;
        }else {
            date=LocalDate.now().toString();
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("amount",String.format("%.2f", matchedValue));
        jsonObject.put("comment",name);
        String mostFrequentType=SocketUtil.remoteCall(name);
        jsonObject.put("category",mostFrequentType.split("、")[0]);
        if(mostFrequentType.split("、").length>=2){
            jsonObject.put("subcategory",mostFrequentType.split("、")[1]);
        }
        else {
            jsonObject.put("subcategory","");
        }
        jsonObject.put("expenditureDate",date);
        return ResultModelUtil.success(jsonObject);
    }

    public static Double convertToDouble(String text) {
        Double result = 0.0;
        Pattern pattern = Pattern.compile("(\\d+元)?(\\d+角)?(\\d+分)?(\\d+块)?(\\d+毛)?(\\d+)?");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String yuan = matcher.group(1);
            if (yuan != null) {
                result += Double.parseDouble(yuan.replace("元", ""));
            }
            String jiao = matcher.group(2);
            if (jiao != null) {
                result += Double.parseDouble(jiao.replace("角", "")) / 10;
            }
            String fen = matcher.group(3);
            if (fen != null) {
                result += Double.parseDouble(fen.replace("分", "")) / 100;
            }
            String kuai = matcher.group(4);
            if (kuai != null) {
                result += Double.parseDouble(kuai.replace("块", ""));
            }
            String mao = matcher.group(5);
            if (mao != null) {
                result += Double.parseDouble(mao.replace("毛", "")) / 10;
            }
            String ling = matcher.group(6);
            if (ling != null) {
                result += Double.parseDouble(ling) / 100;
            }
        }
        return result;
    }
}
