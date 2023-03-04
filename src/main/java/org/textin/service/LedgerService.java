package org.textin.service;


/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:36
 */
public interface LedgerService {
    String getLedger(Long id,String year,String month,Long userId);

    String getChartInfo(String data);
}
