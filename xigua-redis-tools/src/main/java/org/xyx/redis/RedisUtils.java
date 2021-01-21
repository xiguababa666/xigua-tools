package org.xyx.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * description here
 *
 * @author xueyongxin
 */

public class RedisUtils {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 保存缓存，不过期
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 保存缓存，过期时间单位为秒
     *
     * @param key
     * @param value
     * @param expireSeconds
     */
    public void set(String key, String value, long expireSeconds) {
        redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
    }


    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public List<Object> get(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    public void set(Map<String, Object> values) {
        if (CollectionUtils.isEmpty(values)) {
            return;
        }
        redisTemplate.opsForValue().multiSet(values);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

}
