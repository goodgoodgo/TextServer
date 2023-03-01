package org.textin.model.transfer;

import com.alibaba.fastjson.JSON;
import org.springframework.util.ObjectUtils;
import org.textin.dal.dataobject.UserDO;
import org.textin.model.entity.User;
import org.textin.model.enums.UserSexEn;
import org.textin.model.enums.UserStatusEn;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:23
 */
public class UserTransfer {
    public static User toUser(UserDO userDO) {
        if (ObjectUtils.isEmpty(userDO)) {
            return null;
        }

        User user = User.builder()
                .gender(UserSexEn.getEntity(userDO.getGender()))
                .phone(userDO.getPhone())
                .email(userDO.getEmail())
                .status(UserStatusEn.getEntity(userDO.getStatus()))
                .password(userDO.getPassword())
                .username(userDO.getUsername())
                .build();

        user.setId(userDO.getId());
        user.setCreateAt(userDO.getCreateAt());
        user.setUpdateAt(userDO.getUpdateAt());

        return user;
    }

    public static UserDO toUserDO(User user) {
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }

        UserDO userDO = UserDO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        if (!ObjectUtils.isEmpty(user.getStatus())) {
            userDO.setStatus(user.getStatus().getValue());
        }
        if (!ObjectUtils.isEmpty(user.getGender())) {
            userDO.setGender(user.getGender().getValue());
        }
        userDO.setId(user.getId());
        userDO.setCreateAt(user.getCreateAt());
        userDO.setUpdateAt(user.getUpdateAt());

        return userDO;
    }
}
