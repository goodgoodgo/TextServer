package org.textin.dal.dataobject;

import lombok.Data;
import org.textin.model.enums.IsDeletedEn;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 14:31
 */
@Data
public class BaseDO implements Serializable {
    private static final long serialVersionID=1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    public void initBase() {
        this.id = null;
        this.isDelete = IsDeletedEn.NOT_DELETED.getValue();
        this.createAt = new Date();
        this.updateAt = this.createAt;
    }
}
