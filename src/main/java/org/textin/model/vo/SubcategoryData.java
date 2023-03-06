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
 * @create: 2023-03-06 14:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryData {
    private BigDecimal account;
    private String subcategory;
}
