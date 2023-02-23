package org.textin.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2022-12-23 18:54
 */
public class JsonMsgUtil {
    private static AtomicReference<String> price = new AtomicReference<>("");

    private static AtomicReference<String> time = new AtomicReference<>("");
    static Pattern pattern = Pattern.compile("(\\d+\\.\\d+)");
    static Pattern patternTime = Pattern.compile("(\\d{1,2}[/-]\\d{1,2})\\s*(\\d{2}:\\d{2})");


    /**
     * 返回识别价格
     * @param textList
     * @return
     */
    public static String getPrice(List<String> textList) {

        textList.stream().forEach(str -> {
            if (str.matches(".*(\\d+\\.\\d+).*")) {
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    price.set(matcher.group(1) + "");
                }
            }
        });
        return price.get();
    }

    /**
     * 返回识别时间
     * @param textList
     * @return
     */
    public static String getTime(List<String> textList) {
        IntStream.range(0, textList.size()).forEach(i -> textList.set(i, textList.get(i).replace("／", "-")));
        IntStream.range(0, textList.size()).forEach(i -> textList.set(i, textList.get(i).replace("：", ":")));
        textList.stream().forEach(str -> {
            if (str.matches(".*((\\d{4}[/-]\\d{2}[/-]\\d{2})\\s*)?(\\d{2}:\\d{2}).*")) {
                Matcher matcher = patternTime.matcher(str);
                if (matcher.find()) {
                    time.set(matcher.group());
                } else {
                    time.set(str);
                }
            }
        });
        return time.get();
    }

    /**
     * 获取商品
     * @param imgBytes
     * @return
     */
   /* public static String getShopText(byte[] imgBytes){
        List<String> list=new ArrayList<>();
        String shopText="";
        shopText= JSON.parseObject(TextHttpUtil.postUrl("https://api.textin.com/robot/v1.0/api/receipt",imgBytes))
                .getJSONObject("result")
                .getJSONArray("item_list")
                .getJSONObject(5)
                .getString("value");
        String[] lines = shopText.split("\n");
        if (lines.length>=2){
            shopText=lines[lines.length/2];
        }else if (lines.length==1){
            shopText=lines[0];
        }

        List<SegToken> tokens = JiebaSegmenterSingleton.getInstance().process(shopText, JiebaSegmenter.SegMode.SEARCH);
        for (SegToken token : tokens) {
            if (token.word.length()>1 || tokens.size()==1){
                list.add(token.word);
            }
        }
        return list;
    }*/

}
