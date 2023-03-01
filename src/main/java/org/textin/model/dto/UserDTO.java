package org.textin.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 15:00
 */
@Data
public class UserDTO implements Serializable {

    private String email;

    private String code;

    private String password;

}
