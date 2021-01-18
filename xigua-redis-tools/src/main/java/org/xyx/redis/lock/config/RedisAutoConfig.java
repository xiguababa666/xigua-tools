package org.xyx.redis.lock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.xyx.redis.lock.LockAspect;
import org.xyx.redis.lock.reentrant.ReentrantLocker;
import org.xyx.utils.SpringUtils;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2021/1/14 上午10:36
 */

@Import({SpringUtils.class, RedissonBeanConfig.class})
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


}
