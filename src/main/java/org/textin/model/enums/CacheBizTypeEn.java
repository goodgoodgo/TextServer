package org.textin.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 08:58
 */
@Getter
@AllArgsConstructor
public enum CacheBizTypeEn {
    USER_LOGIN_TOKEN("USER_LOGIN_TOKEN", "用户登录凭证 token"),
    TAG_USED("OTHER", "其他缓存事件")
    ;

    private String value;
    private String desc;
}
