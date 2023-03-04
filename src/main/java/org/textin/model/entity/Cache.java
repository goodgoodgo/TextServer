package org.textin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 09:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cache extends BaseEntity {

    private String type;

    private String key;

    private String value;

}
