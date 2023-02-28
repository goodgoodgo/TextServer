package org.textin.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 15:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest extends UserBaseLoginRequest implements Serializable {

    private String phone;

    private String username;

    private String password;

}
