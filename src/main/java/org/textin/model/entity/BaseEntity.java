package org.textin.model.entity;

import lombok.Data;
import org.textin.model.enums.IsDeletedEn;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:06
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionID=1L;

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

    public void initBase() {
        this.id = null;
        this.isDelete = IsDeletedEn.NOT_DELETED.getValue();
        this.createAt = new Date();
        this.updateAt = this.createAt;
    }
}
