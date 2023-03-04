package org.textin.service.Impl;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.textin.dao.CacheDAO;
import org.textin.exception.BizException;
import org.textin.model.entity.Cache;
import org.textin.model.enums.CacheBizTypeEn;
import org.textin.model.enums.ErrorCodeEn;
import org.textin.service.CacheService;
import org.textin.util.SafesUtil;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 09:01
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private CacheDAO cacheDAO;

    /**
     * 所有缓存
     */
    private static final Map<String, StringValue> ALL_CACHE=new ConcurrentHashMap<>();

    /**
     * 更新缓存
     */
    private static final Set<String> MODIFY_CACHE=new HashSet<>();

    /**
     * 更新缓存
     */
    private static final Set<String> NEW_CACHE=new HashSet<>();

    /**
     * 删除缓存
     */
    private static final Set<String> DELETE_KEYS = new HashSet<>();

    @Override
    public boolean set(CacheBizTypeEn bizTypeEn, String key, String value) {
        checkKey(key);
        String buildKey=buildKey(bizTypeEn,key);
        if(ALL_CACHE.containsKey(buildKey)){
            MODIFY_CACHE.add(buildKey);
        }else {
            NEW_CACHE.add(buildKey);
        }
        ALL_CACHE.put(buildKey,new StringValue(bizTypeEn.getValue(),buildKey));
        return Boolean.TRUE;
    }

    @Override
    public boolean setAndExpire(CacheBizTypeEn bizType, String key, String value, Long seconds) {
        checkKey(key);
        String buildKey=buildKey(bizType,key);
        if(ALL_CACHE.containsKey(buildKey)){
            MODIFY_CACHE.add(buildKey);
        }else {
            NEW_CACHE.add(buildKey);
        }
        ALL_CACHE.put(buildKey,new StringValue(bizType.getValue(),value,seconds));
        return Boolean.TRUE;
    }

    @Override
    public String get(CacheBizTypeEn bizType, String key) {
        checkKey(key);
        StringValue stringValue = ALL_CACHE.get(buildKey(bizType, key));
        return stringValue ==null ? null : stringValue.getValue();
    }

    @Override
    public Boolean exists(CacheBizTypeEn bizType, String key) {
        checkKey(key);
        return ALL_CACHE.containsKey(buildKey(bizType,key));
    }

    @Override
    public Boolean del(CacheBizTypeEn bizType, String key) {
        checkKey(key);
        String buildKey=buildKey(bizType,key);
        ALL_CACHE.remove(buildKey);
        DELETE_KEYS.add(buildKey);
        return Boolean.TRUE;
    }

    private void checkKey(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new BizException(ErrorCodeEn.COMMON_CACHE_KEY_EMPTY);
        }
    }


    // -------------------------------- 缓存更新操作

    @PreDestroy
    private void preDestroy(){
        preDestroy();
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    public void task(){

        SafesUtil.ofMap(ALL_CACHE).forEach((k,v)->{
            if(v.getExpire()!=-1L&&System.currentTimeMillis()>= v.getExpire()){
                ALL_CACHE.remove(k);
                DELETE_KEYS.add(k);
            }
        });
        persistence();
    }

    private void persistence(){
        if(!CollectionUtils.isEmpty(DELETE_KEYS)){
            cacheDAO.batchDeleteByKeys(DELETE_KEYS);
            DELETE_KEYS.clear();
        }

        List<Cache> newCache=new ArrayList<>();
        ALL_CACHE.forEach((key,stringValue)->{
            if(MODIFY_CACHE.contains(key)){
                cacheDAO.updateByKey(key, JSON.toJSONString(stringValue));
            }
            if (NEW_CACHE.contains(key)) {
                Cache cache = Cache.builder()
                        .key(key)
                        .value(JSON.toJSONString(stringValue))
                        .type(stringValue.getType())
                        .build();
                cache.initBase();
                newCache.add(cache);
            }
        });
        MODIFY_CACHE.clear();
        if(newCache.size()!=0){
            newCache.forEach(cache -> {
                try {
                    cacheDAO.insert(cache);
                } catch (Exception e) {}
            });
        }
        NEW_CACHE.clear();
    }

    private String buildKey(CacheBizTypeEn bizType, String key) {
        return buildKey(bizType.getValue(), key);
    }

    private String buildKey(String bizType, String key) {
        return bizType + ":" + key;
    }

    @Data
    @NoArgsConstructor
    private static class StringValue {
        private String value;
        private Long expire;
        private String type;

        private StringValue(String type, String value, Long seconds) {
            this.type = type;
            this.value = value;
            this.expire = System.currentTimeMillis() + seconds * 1000;
        }

        private StringValue(String type, String value) {
            this.type = type;
            this.value = value;
            this.expire = -1L;
        }
    }

}
