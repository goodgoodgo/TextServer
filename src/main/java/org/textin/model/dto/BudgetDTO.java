package org.textin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-11 10:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetDTO {
    private Long userId;
    private BigDecimal budget;
    private String month;
    private String year;
}
