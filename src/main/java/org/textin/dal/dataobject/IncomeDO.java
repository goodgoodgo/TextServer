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
 * @create: 2023-02-24 14:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeDO extends BaseDO {
    /**
     * 收入
     */
    private BigDecimal amount;

    /**
     * 收入类别
     */
    private String category;

    /**
     * 收入二级类别
     */
    private String subcategory;

    /**
     * 备注
     */
    private String comment;

    /**
     * 创建者id
     */
    private Long creatorId;

    /**
     * 归属账本id
     */
    private Long ledgerId;
}
