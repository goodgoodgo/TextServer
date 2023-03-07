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
 * @create: 2023-03-07 09:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillVO {
    private String date;
    private BigDecimal income;
    private BigDecimal expenditure;
    private BigDecimal balance;
}
