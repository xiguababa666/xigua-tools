package org.xyx.redis.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.xyx.redis.RedisUtils;
import org.xyx.redis.cache.BaseCacher;
import org.xyx.redis.cache.Cacher;
import org.xyx.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.*;

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
        if (val != null) {
            return JsonUtil.jsonStr2Obj(val, clazz);
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
