package org.textin.model.enums;

import lombok.Getter;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 14:33
 */
public enum IsDeletedEn {
    DELETED(1),
    NOT_DELETED(0),
    ;

    @Getter
    private Integer value;

    IsDeletedEn(Integer value) {
        this.value = value;
    }
}
