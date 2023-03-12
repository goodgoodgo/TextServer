package org.textin.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-11 21:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    private Long userId;
    private String email;
    private String userName;
}
