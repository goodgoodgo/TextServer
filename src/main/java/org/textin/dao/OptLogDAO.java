package org.textin.dao;

import org.textin.model.entity.OptLog;

import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 10:41
 */
public interface OptLogDAO {
    void insert(OptLog optLog);

    List<OptLog> query(OptLog optLog);
}
