package org.textin.util;

import org.springframework.util.ObjectUtils;
import org.textin.exception.BizException;
import org.textin.model.enums.ErrorCodeEn;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-25 10:11
 */
public class CheckUtil {
    private CheckUtil(){
    };
    /**
     * 检查请求路径参数是否为空
     *
     * @param o
     */
    public static void checkParamToast(Object o, String message) {
        if (ObjectUtils.isEmpty(o)) {
            throw new BizException(ErrorCodeEn.PARAM_CHECK_ERROR.getCode(),
                    MessageFormat.format(ErrorCodeEn.PARAM_CHECK_ERROR.getMessage(), message));
        }
    }

    /**
     * 检测对象是否为空
     * @param o
     * @param errorCode
     */
    public static void isEmpty(Object o, ErrorCodeEn errorCode) {
        if (ObjectUtils.isEmpty(o)) {
            throw new BizException(errorCode);
        }
    }

    public static void isNotEmpty(Object o, ErrorCodeEn errorCode) {
        if (!ObjectUtils.isEmpty(o)) {
            throw new BizException(errorCode);
        }
    }

    public static void isPhoneNumberValid(String phoneNumber,ErrorCodeEn errorCode) {
        String regex = "^1[3-9]\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if(!matcher.matches()){
            throw new BizException(errorCode);
        }
    }

    public static void isEmailValid(String email,ErrorCodeEn errorCode) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            throw new BizException(errorCode);
        }
    }

}
