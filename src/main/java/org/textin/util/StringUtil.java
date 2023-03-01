package org.textin.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;
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

    private static final String MD5_PRE_KEY = "1357924680QWERqwer";
    private static final String MD5_POST_KEY = "0987654321zxcv";
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

    public static String md5UserPassword(String password) {
        return md5(MD5_PRE_KEY + password + MD5_POST_KEY);
    }

    /**
     * 对 value 进行 md5 加密
     * @param value
     * @return
     */
    public static String md5(String value) {
        return DigestUtils.md5Hex(value);
    }

    /**
     * json string 属性值最大长度
     */
    private static final Integer MAX_LENGTH_FOR_PER_PROPERTY = 100;

    public static String toJSONString(Object result) {
        return JSONObject.toJSONString(result
                , new ValueFilter() {
                    @Override
                    public Object process(Object object, String name, Object value) {
                        if(value instanceof String && ((String) value).length() > MAX_LENGTH_FOR_PER_PROPERTY){//String只打印前500个字符
                            return ((String) value).substring(0, MAX_LENGTH_FOR_PER_PROPERTY) + "...";
                        }
                        if(value instanceof byte[]){
                            String s = new String((byte[]) value, Charset.defaultCharset());
                            if(s.length() > MAX_LENGTH_FOR_PER_PROPERTY){
                                return s.substring(0, MAX_LENGTH_FOR_PER_PROPERTY) + "...";
                            }
                        }
                        return value;
                    }
                }
                , SerializerFeature.IgnoreNonFieldGetter);
    }


}
