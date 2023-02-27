package org.textin.model.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 10:46
 */
@AllArgsConstructor
@Getter
public enum OptLogTypeEn {
    USER_LOGIN("USER_LOGIN", "用户登录记录"),
    USER_LOGOUT("USER_LOGOUT", "用户登出记录"),
    USER_REGISTER("USER_REGISTER", "用户注册记录"),
    ;

    private String value;
    private String desc;

    public static OptLogTypeEn getEntity(String value) {
        for (OptLogTypeEn userSexEn : values()) {
            if (userSexEn.getValue().equalsIgnoreCase(value)) {
                return userSexEn;
            }
        }

        return null;
    }
}
