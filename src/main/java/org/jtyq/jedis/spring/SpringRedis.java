package org.jtyq.jedis.spring;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class SpringRedis {

    public static void main(String[] args) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
    }
}
