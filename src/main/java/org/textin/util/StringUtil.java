package org.textin.util;

import java.util.Random;
import java.util.UUID;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 10:51
 */
public class StringUtil {
    public  StringUtil(){
    }
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generateCode(){
        String codeChars = "0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(codeChars.length());
            stringBuilder.append(codeChars.charAt(index));
        }
        return stringBuilder.toString();
    }
}
