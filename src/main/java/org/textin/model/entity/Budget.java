package org.textin.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-03 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Budget extends BaseEntity{
    private Date budgetDate;
    private BigDecimal budget;
    private Long userId;
}
