package org.textin.dal.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.textin.model.enums.UserSexEn;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 14:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDO extends BaseDO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态
     */
    private String status;
}
