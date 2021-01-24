package org.xyx.redis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2021/1/23 下午10:04
 */

@Component
public class CacheProxy {

    private static final Logger logger = LoggerFactory.getLogger(CacheProxy.class);


    @CacheData(key = "xyx", type = CacheType.LOCAL)
    public String test() {
        logger.info("[CacheProxy] test in --------->");
        return "the value of key 'xyx'";
    }


    @CacheData(key = "xyx_%s", type = CacheType.LOCAL)
    public String test(String v) {
        logger.info("[CacheProxy] test({}) in --------->", v);
        return String.format("the value of key 'xyx_%s'", v);
    }

}
