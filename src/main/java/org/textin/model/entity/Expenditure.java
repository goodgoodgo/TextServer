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
 * @create: 2023-02-24 09:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expenditure extends BaseEntity{

    private BigDecimal amount;

    private String category;

    private String subcategory;

    private String comment;

    private Long creatorId;

    private Long ledgerId;

}
