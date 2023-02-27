package org.textin.service.Impl;

import org.springframework.stereotype.Service;
import org.textin.dal.dao.LedgerDAO;
import org.textin.model.entity.Ledger;
import org.textin.model.transfer.LedgerTransfer;
import org.textin.service.LedgerService;

import javax.annotation.Resource;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:50
 */
@Service
public class LedgerServiceImpl implements LedgerService {

    @Resource
    private LedgerDAO ledgerDAO;

    @Override
    public Ledger get(Long id) {
        Ledger ledger= LedgerTransfer.toLedger(ledgerDAO.get(id));
        return ledger;
    }
}
