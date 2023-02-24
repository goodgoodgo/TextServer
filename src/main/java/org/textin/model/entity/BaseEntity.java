package org.textin.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:06
 */
@Data
public abstract class BaseEntity {
    private Long id;
    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 是否删除
     */
    private Integer isDelete;
}
