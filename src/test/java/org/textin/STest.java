package org.textin;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;


/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:25
 */
@SpringBootTest
public class STest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testString(){
        System.out.printf(stringRedisTemplate.opsForValue().get("login:code:2440240762@qq.com"));
    }
}
