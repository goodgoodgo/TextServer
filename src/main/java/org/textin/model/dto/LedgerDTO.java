package org.textin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-12 09:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerDTO {
    private Long userId;
    private String name;
}
