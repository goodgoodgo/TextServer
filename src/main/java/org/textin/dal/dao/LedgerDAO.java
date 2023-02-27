package org.textin.dal.dao;

import org.textin.dal.dataobject.LedgerDO;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:13
 */
public interface LedgerDAO {
    LedgerDO get(Long userId);
}
