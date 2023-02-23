package org.textin.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:45
 */
@Getter
@AllArgsConstructor
public enum UserStatusEn {
    ACTIVE("ACTIVE","激活"),
    IN_ACTIVE("IN_ACTIVE","未激活");

    private String value;

    private String dsc;

    public static UserStatusEn getEntity(String value){

        for (UserStatusEn userStatusEn:values()){
            if(userStatusEn.getValue().equalsIgnoreCase(value)){
                return userStatusEn;
            }
        }

        return null;
    }
}
