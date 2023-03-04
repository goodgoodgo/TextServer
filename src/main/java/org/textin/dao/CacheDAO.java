package org.textin.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.model.entity.Cache;

import java.util.List;
import java.util.Set;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 09:14
 */
public interface CacheDAO {
    void insertBatch(List<Cache> CacheS);

    void insert(Cache Cache);

    List<Cache> getAll();

    void batchDeleteByKeys(@Param("keys") Set<String> keys);

    void updateByKey(@Param("key") String key, @Param("value") String value);
}
