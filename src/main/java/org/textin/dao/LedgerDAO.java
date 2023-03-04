package org.textin.dao;

import org.textin.model.entity.Ledger;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:13
 */
public interface LedgerDAO {
    Ledger get(Long userId);
}
