package org.textin.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-06 10:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeVO {
    private BigDecimal account;
    private String date;
    private String category;
    private List<SubcategoryData> subcategoryDate;
}
