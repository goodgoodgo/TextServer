package org.textin.model.transfer;

import org.springframework.util.ObjectUtils;
import org.textin.dal.dataobject.UserDO;
import org.textin.model.entity.User;
import org.textin.model.enums.UserSexEn;
import org.textin.model.enums.UserStatusEn;

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
                .status(UserStatusEn.getEntity(userDO.getStatus()))
                .password(userDO.getPassword())
                .username(userDO.getUsername())
                .build();

        user.setId(userDO.getId());
        user.setCreateAt(userDO.getCreateAt());
        user.setUpdateAt(userDO.getUpdateAt());

        return user;
    }
}
