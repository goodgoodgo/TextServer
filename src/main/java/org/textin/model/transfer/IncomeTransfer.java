package org.textin.model.transfer;

import org.textin.dal.dataobject.IncomeDO;
import org.textin.model.entity.Income;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-25 09:34
 */
public class IncomeTransfer {

    public static IncomeDO toIncomeDO(Income income){
        IncomeDO incomeDO=IncomeDO.builder()
                .comment(income.getComment())
                .amount(income.getAmount())
                .category(income.getCategory())
                .subcategory(income.getSubcategory())
                .creatorId(income.getCreatorId())
                .ledgerId(income.getLedgerId())
                .build();
        return incomeDO;
    }
}
