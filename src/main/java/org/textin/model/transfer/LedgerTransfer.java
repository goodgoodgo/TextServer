package org.textin.model.transfer;

import org.springframework.util.ObjectUtils;
import org.textin.dal.dataobject.LedgerDO;
import org.textin.model.entity.Ledger;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:46
 */
public class LedgerTransfer {
    public static Ledger toLedger(LedgerDO ledgerDO){
        if (ObjectUtils.isEmpty(ledgerDO)) {
            return null;
        }
        Ledger ledger=Ledger.builder()
                .expenditure(ledgerDO.getExpenditure())
                .income(ledgerDO.getIncome())
                .name(ledgerDO.getName())
                .userId(ledgerDO.getUserId())
                .build();
        return ledger;
    }
}
