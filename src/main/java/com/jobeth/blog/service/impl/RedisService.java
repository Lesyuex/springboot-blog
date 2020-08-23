package com.jobeth.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jobeth.blog.common.utils.DateUtil;
import com.jobeth.blog.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/6/30 15:45
 */
@Service
@Slf4j
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 保存key - value
     *
     * @param key key
     * @param val val
     * @return boolean
     */
    public boolean set(String key, Object val) {
        boolean success = false;
        ValueOperations<String, Object> operations = null;
        try {
            operations = redisTemplate.opsForValue();
            operations.set(key, val);
            success = true;
            log.info("【 key => {} 数据存入 Redis 缓存成功 】", key);
        } catch (Exception e) {
            log.error("【 key => {} 数据存入 Redis 缓存发生错误 -】", key, e);
        }
        return success;

    }

    /**
     * 根据key设置有效时间
     *
     * @param key        key
     * @param expireTime time
     */
    public void setExpire(String key, Object value, long expireTime) {
        ValueOperations<String, Object> operations = null;
        try {
            operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            Date date = new Date(Clock.systemDefaultZone().millis() + expireTime);
            log.info("【 key => {} 数据存入 Redis 缓存成功,失效时间：{} 】", key,  DateUtil.dateString(date));
        } catch (Exception e) {
            log.error("【 key => {} 数据存入 Redis 缓存发生错误 -】", key, e);
            throw e;
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key key
     * @return boolean
     */
    public boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("【 从 Redis 查找 key => {} 缓存数据时发生错误 -】", key, e);
            return false;
        }
    }

    /**
     * 读取缓存
     *
     * @param key key
     * @return Object
     */
    public Object get(String key) {
        Object value = null;
        ValueOperations<String, Object> operations = null;
        try {
            operations = redisTemplate.opsForValue();
            value = operations.get(key);
        } catch (Exception e) {
            log.error("【 从 Redis 获取 key => {} 缓存发生错误 】", key, e);
        }
        return value;
    }

    /**
     * 删除对应的value
     *
     * @param key key
     */
    public boolean remove(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("【 从 Redis 删除key => {} 缓存发生错误 】", key, e);
            return false;
        }
    }

    /**
     * redisTemplate删除迷糊匹配的key的缓存
     */
    public void deleteByPrefix(String prefix) {

        Set<String> keys = null;
        try {
            keys = redisTemplate.keys(prefix);
            if (CollectionUtils.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
                log.info("【 清除 Redis  keys 数据成功 => {} 】", keys);
            }
        } catch (Exception e) {
            log.error("【 从 Redis 删除 keys => {} 缓存发生错误 】", keys, e);
        }

    }

    public <T> T get(String key, Class<T> clazz) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String json = operations.get(key);
        return JacksonUtil.jsonToPojo(json, clazz);
    }
}
