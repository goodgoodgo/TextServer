package org.textin.service;

import org.textin.model.enums.CacheBizTypeEn;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 08:55
 */
public interface CacheService {
    /**
     * 存储
     * @param bizTypeEn
     * @param key
     * @param value
     * @return
     */
    boolean set(CacheBizTypeEn bizTypeEn,String key,String value);

    /**
     * 存储并设置超时时长(单位:秒)
     * @param bizType
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    boolean setAndExpire(CacheBizTypeEn bizType, String key, String value, Long seconds);
    /**
     * 获取值
     * @param key
     * @return
     */
    String get(CacheBizTypeEn bizType, String key);

    /**
     * 判断是否存在
     * @param key
     * @return
     */
    Boolean exists(CacheBizTypeEn bizType, String key);

    /**
     * 删除
     * @param key
     * @return
     */
    Boolean del(CacheBizTypeEn bizType, String key);
}
