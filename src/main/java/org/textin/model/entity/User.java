package org.textin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.textin.model.enums.UserSexEn;
import org.textin.model.enums.UserStatusEn;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{


    private String username;

    private String password;

    private String gender;

    private String phone;

    private String email;

    private String status;

}
