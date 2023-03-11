package org.textin.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-11 09:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetVO {
    private String date;
    private BigDecimal balance;
    private BigDecimal budget;
    private BigDecimal expenditure;
}
