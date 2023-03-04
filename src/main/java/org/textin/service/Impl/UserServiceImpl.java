package org.textin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.textin.dao.UserDAO;
import org.textin.model.entity.User;
import org.textin.model.enums.CacheBizTypeEn;
import org.textin.model.enums.ErrorCodeEn;
import org.textin.model.enums.UserSexEn;
import org.textin.model.dto.UserDTO;
import org.textin.service.CacheService;
import org.textin.service.UserService;
import org.textin.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.textin.constant.Constant.*;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:33
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDao;

    @Resource
    private CacheService cacheService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录凭证 token 过期时长（单位：秒）,7天
     */
    private static final Long USER_LOGIN_TOKEN_EXPIRE_TIMEOUT = 60 * 60 * 24 * 7L;

    @Override
    public User get(Long id) {
        User user= userDao.findById(id);
        return user;
    }

    @Override
    public String sendCode(String email) {
        CheckUtil.isEmailValid(email, ErrorCodeEn.EMAIL_NOT_VAlID);
        String code= StringUtil.generateCode();
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+email,code,LOGIN_CODE_TTL, TimeUnit.MINUTES);
        MailUtils.sendEmail(code,email);
        return JSON.toJSONString(ResultModelUtil.success("验证码发生成功"));
    }

    @Override
    public String login(UserDTO loginForm, HttpServletRequest request) {
        CheckUtil.isEmailValid(loginForm.getEmail(),ErrorCodeEn.EMAIL_NOT_VAlID);
        String oldToken = request.getHeader("authorization");
        String key  = LOGIN_USER_KEY + oldToken;
        User user=userDao.findUserByEmail(loginForm.getEmail());
        UserDTO userDTO = BeanUtil.copyProperties(user,UserDTO.class);
        if (!StringUtil.md5UserPassword(loginForm.getPassword()).equals(user.getPassword())){
            return JSON.toJSONString(ResultModelUtil.success("密码错误"));
        }
        Map<Object, Object> oldUserMap = stringRedisTemplate.opsForHash().entries(key);
        UserHolder.saveUser(userDTO);
        EventBus.emit(EventBus.MsgType.USER_LOGIN,user);
        //token过期
        if (oldUserMap.isEmpty()) {
            String token = StringUtil.generateUUID();
            Map<String,Object> userMap=BeanUtil.beanToMap(userDTO);
            stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY+token,userMap);
            stringRedisTemplate.expire(LOGIN_USER_KEY+token,LOGIN_USER_TTL,TimeUnit.DAYS);
            cacheLoginUser(token, user);
            return JSON.toJSONString(ResultModelUtil.success(token));
        }else {
            return JSON.toJSONString(ResultModelUtil.success());
        }
    }

    @Override
    public String register(UserDTO registerForm) {
        CheckUtil.isEmailValid(registerForm.getEmail(),ErrorCodeEn.EMAIL_NOT_VAlID);
        String cacheCode =stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY+registerForm.getEmail());
        String code=registerForm.getCode();
        if(cacheCode==null||!cacheCode.equals(code)){
            return JSON.toJSONString(ResultModelUtil.fail(0,"验证码错误"));
        }
        User userOld=userDao.findUserByEmail(registerForm.getEmail());
        if (userOld!=null){
            return JSON.toJSONString(ResultModelUtil.fail(404,"用户已存在"));
        }
        User user=User.builder().gender(UserSexEn.MAN.getValue())
                .password(StringUtil.md5UserPassword(registerForm.getPassword()))
                .username(StringUtil.generateUUID())
                .email(registerForm.getEmail())
                .build();
        user.setCreateAt(new Date());
        userDao.insert(user);
        UserDTO userDTO = BeanUtil.copyProperties(user,UserDTO.class);
        String token = StringUtil.generateUUID();
        Map<String,Object> userMap=BeanUtil.beanToMap(userDTO);
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY+token,userMap);
        stringRedisTemplate.expire(LOGIN_USER_KEY+token,LOGIN_USER_TTL,TimeUnit.DAYS);
        cacheLoginUser(token, user);
        EventBus.emit(EventBus.MsgType.USER_REGISTER,user);
        return JSON.toJSONString(ResultModelUtil.success(token));
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
