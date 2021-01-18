package org.xyx.redis.lock.config;

import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xyx.redis.lock.redisson.RedissonLocker;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2021/1/14 上午11:33
 */

@Configuration
@ConditionalOnClass({RedissonClient.class})
public class RedissonBeanConfig {

    @Bean
    @ConditionalOnBean(value = RedissonClient.class)
    public RedissonLocker redissonLocker(RedissonClient redissonClient) {
        return new RedissonLocker(redissonClient);
    }

}
