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
 * @create: 2023-02-24 14:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerDO extends BaseDO {
    /**
     * 账本名称
     */
    private String name;

    /**
     * 支出额度
     */
    private BigDecimal expenditure;

    /**
     * 收入额度
     */
    private BigDecimal income;

    /**
     * 所属用户ID
     */
    private Long userId;

    /**
     * 预算
     */
    private BigDecimal budget;
}
