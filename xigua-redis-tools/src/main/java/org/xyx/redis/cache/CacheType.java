package org.xyx.redis.cache;

import org.xyx.redis.cache.local.LocalCacher;
import org.xyx.redis.cache.redis.RedisCacher;
import org.xyx.utils.SpringUtils;

/**
 * 缓存类型
 *
 * @author xueyongxin
 */
public enum CacheType {

    // redis
    REDIS(RedisCacher.class),

    // 本地
    LOCAL(LocalCacher.class),

    ;

    private final Class<? extends Cacher> clazz;


    private Cacher cacher;


    CacheType(Class<? extends Cacher> clazz) {
        this.clazz = clazz;
    }


    public Cacher getCacher() {
        if (cacher == null) {
            cacher = SpringUtils.getBean(clazz);
        }
        return cacher;
    }

}
