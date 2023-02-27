package org.textin.model.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.textin.model.enums.OptLogTypeEn;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 10:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptLog {
    private OptLogTypeEn type;

    private Long operatorId;

    private String content;

    public static OptLog createUserRegisterRecordLog(Long operatorId, User user) {
        return OptLog.builder()
                .type(OptLogTypeEn.USER_REGISTER)
                .operatorId(operatorId)
                .content(JSON.toJSONString(user))
                .build();
    }

    public static OptLog createUserLoginRecordLog(Long operatorId, String content) {
        return OptLog.builder()
                .type(OptLogTypeEn.USER_LOGIN)
                .operatorId(operatorId)
                .content(content)
                .build();
    }

    public static OptLog createUserLogoutRecordLog(Long operatorId, String content) {
        return OptLog.builder()
                .type(OptLogTypeEn.USER_LOGOUT)
                .operatorId(operatorId)
                .content(content)
                .build();
    }
}
