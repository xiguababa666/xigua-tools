package org.xyx.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.xyx.redis.RedisUtils;

/**
 * description here
 *
 * @author xueyongxin
 * @date 2021/1/14 上午11:33
 */

@Configuration
@ConditionalOnClass({RedisTemplate.class})
public class RedisBeanConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisBeanConfig.class);

    @Bean
    @ConditionalOnBean(value = RedisTemplate.class)
    public RedisUtils redisUtil(RedisTemplate<String, Object> redisTemplate) {
        logger.info("[WengerConfig] *RedisUtil*");
        return new RedisUtils(redisTemplate);
    }

}
