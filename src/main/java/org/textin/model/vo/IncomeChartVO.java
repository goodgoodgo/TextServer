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
 * @create: 2023-03-08 10:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeChartVO {
    private BigDecimal account;
    private String date;
}
