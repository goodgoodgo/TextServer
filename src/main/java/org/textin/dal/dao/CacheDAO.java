package org.textin.dal.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.dal.dataobject.CacheDO;

import java.util.List;
import java.util.Set;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 09:14
 */
public interface CacheDAO {
    void insertBatch(List<CacheDO> cacheDOS);

    void insert(CacheDO cacheDO);

    List<CacheDO> getAll();

    void batchDeleteByKeys(@Param("keys") Set<String> keys);

    void updateByKey(@Param("key") String key, @Param("value") String value);
}
