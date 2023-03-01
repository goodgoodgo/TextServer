package org.textin.manager;

import com.alibaba.fastjson.JSON;
import org.springframework.util.ObjectUtils;
import org.textin.model.entity.OptLog;
import org.textin.model.entity.User;
import org.textin.model.enums.CacheBizTypeEn;
import org.textin.service.CacheService;
import org.textin.util.EventBus;
import org.textin.util.StringUtil;

import javax.annotation.Resource;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 10:29
 */
public class AbstractLoginManager {

    @Resource
    private CacheService cacheService;

    /**
     * 用户登录凭证 token 过期时长（单位：秒）,7天
     */
    private static final Long USER_LOGIN_TOKEN_EXPIRE_TIMEOUT = 60 * 60 * 24 * 7L;

    protected String login(User user) {
        // 缓存登录凭证 token =》 user
        String token = StringUtil.generateUUID();
        cacheLoginUser(token, user);

        // 触发保存操作日志事件
        EventBus.emit(EventBus.MsgType.USER_LOGIN, OptLog.createUserLoginRecordLog(user.getId(), JSON.toJSONString(user)));

        return token;
    }

    protected void cacheLoginUser(String token, User user) {
        // 删除之前登录缓存
        deleteLoginUser(user.getId());

        // 重新保存缓存
        cacheService.setAndExpire(CacheBizTypeEn.USER_LOGIN_TOKEN
                , String.valueOf(user.getId()), token, USER_LOGIN_TOKEN_EXPIRE_TIMEOUT);
        cacheService.setAndExpire(CacheBizTypeEn.USER_LOGIN_TOKEN
                , token, JSON.toJSONString(user), USER_LOGIN_TOKEN_EXPIRE_TIMEOUT);
    }

    protected void deleteLoginUser(Long userId) {
        // 删除之前登录缓存
        String oldToken = cacheService.get(CacheBizTypeEn.USER_LOGIN_TOKEN, String.valueOf(userId));
        if (!ObjectUtils.isEmpty(oldToken)) {
            cacheService.del(CacheBizTypeEn.USER_LOGIN_TOKEN, String.valueOf(userId));
            cacheService.del(CacheBizTypeEn.USER_LOGIN_TOKEN, oldToken);
        }
    }
}
