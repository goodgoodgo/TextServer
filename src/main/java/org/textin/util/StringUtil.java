package org.textin.util;

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
}
