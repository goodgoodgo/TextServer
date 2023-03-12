package org.textin.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-12 16:27
 */
public class ChineseNumberConverterUtil {
    private static final Map<Character, Integer> NUMBER_MAP = new HashMap<>();
    private static final Pattern PATTERN = Pattern.compile("[一二三四五六七八九零十百千万]+");

    static {
        NUMBER_MAP.put('零', 0);
        NUMBER_MAP.put('一', 1);
        NUMBER_MAP.put('二', 2);
        NUMBER_MAP.put('三', 3);
        NUMBER_MAP.put('四', 4);
        NUMBER_MAP.put('五', 5);
        NUMBER_MAP.put('六', 6);
        NUMBER_MAP.put('七', 7);
        NUMBER_MAP.put('八', 8);
        NUMBER_MAP.put('九', 9);
        NUMBER_MAP.put('十', 10);
        NUMBER_MAP.put('百', 100);
        NUMBER_MAP.put('千', 1000);
        NUMBER_MAP.put('万', 10000);
    }

    public static String convert(String text) {
        Matcher matcher = PATTERN.matcher(text);
        while (matcher.find()) {
            String chineseNumber = matcher.group();
            int arabicNumber = chineseToArabic(chineseNumber);
            text = text.replace(chineseNumber, String.valueOf(arabicNumber));
        }
        return text;
    }

    private static int chineseToArabic(String chineseNumber) {
        int result = 0;
        int temp = 1;
        int num = 0;
        for (int i = 0; i < chineseNumber.length(); i++) {
            char c = chineseNumber.charAt(i);
            int n = NUMBER_MAP.get(c);
            if (n >= 10) {
                if (temp == 1) {
                    temp = n;
                } else {
                    num += temp * n;
                    temp = 1;
                }
            } else {
                temp = n;
                if (i == chineseNumber.length() - 1 || NUMBER_MAP.get(chineseNumber.charAt(i + 1)) < 10) {
                    num += temp;
                    temp = 1;
                }
            }
        }
        result += num * temp;
        return result;
    }

    public static String ChineseNumberReplace(String text) {
        text = text.replace("两", "二");
        String result = convert(text);
        return result; // 输出：这件商品的价格是32块2毛8。
    }
}
