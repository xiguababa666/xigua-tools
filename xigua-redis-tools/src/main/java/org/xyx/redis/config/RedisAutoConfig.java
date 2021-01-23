package org.xyx.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.xyx.redis.cache.CacheAspect;
import org.xyx.redis.lock.LockAspect;
import org.xyx.redis.lock.reentrant.ReentrantLocker;
import org.xyx.utils.SpringUtils;

/**
 * description here
 *
 * @author xueyongxin
 */

@Import({SpringUtils.class, RedissonBeanConfig.class, RedisBeanConfig.class})
@Configuration
public class RedisAutoConfig {

    @Bean
    public LockAspect lockAspect() {
        return new LockAspect();
    }


    @Bean
    public ReentrantLocker reentrantLocker() {
        return new ReentrantLocker();
    }


    @Bean
    public CacheAspect cacheAspect() {
        return new CacheAspect();
    }
}
