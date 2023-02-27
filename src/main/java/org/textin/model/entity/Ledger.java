package org.textin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Ledger extends BaseEntity{


    private String name;

    private BigDecimal expenditure;

    private BigDecimal income;

    private Long userId;

    private BigDecimal budget;
}
