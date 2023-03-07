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
 * @create: 2023-03-07 09:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeYearVO {
    private BigDecimal account;
    private String date;
}
