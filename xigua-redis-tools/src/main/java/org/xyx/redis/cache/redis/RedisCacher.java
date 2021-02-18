package org.xyx.redis.cache.redis;

import com.fasterxml.jackson.databind.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.xyx.redis.RedisUtils;
import org.xyx.redis.cache.BaseCacher;
import org.xyx.redis.cache.Cacher;
import org.xyx.utils.JsonUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * description here
 *
 * @author xueyongxin
 */

@Lazy
@Component
public class RedisCacher extends BaseCacher implements Cacher {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacher.class);

    @Resource
    private RedisUtils redisUtil;

    @Override
    public Object get(String key, Class<?> clazz) {
        String val = (String) redisUtil.get(key);
        if (val == null) {
            return null;
        }
        if (String.class.isAssignableFrom(clazz)) {
            return val;
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return Integer.valueOf(val);
        } else if (Long.class.isAssignableFrom(clazz)) {
            return Long.valueOf(val);
        } else if (Short.class.isAssignableFrom(clazz)) {
            return Short.valueOf(val);
        } else if (Byte.class.isAssignableFrom(clazz)) {
            return Byte.valueOf(val);
        } else if (Double.class.isAssignableFrom(clazz)) {
            return Double.valueOf(val);
        } else if (Float.class.isAssignableFrom(clazz)) {
            return Float.valueOf(val);
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            return Boolean.valueOf(val);
        } else if (BigDecimal.class.isAssignableFrom(clazz)) {
            return new BigDecimal(val);
        }
        return JsonUtil.jsonStr2Obj(val, clazz);
    }

    @Override
    public Object get(String key, JavaType javaType) {
        String val = (String) redisUtil.get(key);
        if (val != null) {
            return JsonUtil.jsonStr2Obj(val, javaType);
        }
        return null;
    }

    @Override
    public Map<String, Object> get(List<String> keys) {
        List<Object> ret = redisUtil.get(keys);
        Map<String, Object> map = new LinkedHashMap<>();
        int keySize = keys.size(), retSize = ret.size();
        if (keySize != retSize) {
            logger.error("[RedisCacher] redis cache value error! keys:{}, keys size:{}, value size:{}", keys, keySize, retSize);
            return Collections.emptyMap();
        }
        for (int i = 0; i < keySize; i++) {
            map.put(keys.get(i), ret.get(i));
        }
        return map;
    }

    @Override
    public void set(String key, Object v) {
        if (v instanceof String) {
            redisUtil.set(key, v);
        } else {
            redisUtil.set(key, JsonUtil.obj2JsonStr(v));
        }
    }

    @Override
    public void set(String key, Object v, long time) {

    }

}
