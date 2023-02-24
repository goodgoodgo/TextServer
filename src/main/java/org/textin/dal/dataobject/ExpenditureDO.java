package org.textin.dal.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 14:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenditureDO extends BaseDO {

    /**
     * 支出额度
     */
    private BigDecimal amount;

    /**
     * 支出类别
     */
    private String category;

    /**
     * 二级分类
     */
    private String subcategory;

    /**
     * 备注
     */
    private String comment;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 所属账本ID
     */
    private Long ledgerId;
}
