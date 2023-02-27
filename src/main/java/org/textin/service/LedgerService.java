package org.textin.service;

import org.textin.model.entity.Ledger;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:36
 */
public interface LedgerService {
    Ledger get(Long id);
}
