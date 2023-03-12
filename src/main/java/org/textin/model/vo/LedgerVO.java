package org.textin.model.vo;

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
public class LedgerVO {
    private String name;
    private String top;
    private Long id;
}
