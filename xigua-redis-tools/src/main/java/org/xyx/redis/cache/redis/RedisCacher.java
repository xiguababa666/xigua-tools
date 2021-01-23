package org.xyx.redis.cache.redis;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.xyx.redis.RedisUtils;
import org.xyx.redis.cache.BaseCacher;
import org.xyx.redis.cache.Cacher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * description here
 *
 * @author xueyongxin
 */

@Lazy
@Component
public class RedisCacher extends BaseCacher implements Cacher {

    @Resource
    private RedisUtils redisUtil;

    @Override
    public Object get(String key) {
        return redisUtil.get(key);
    }

    @Override
    public List<Object> get(Collection<String> keys) {
        return redisUtil.get(keys);
    }

    @Override
    public void set(String key, Object v) {
        redisUtil.set(key, v);
    }

    @Override
    public void set(String key, Object v, long time) {

    }

}
