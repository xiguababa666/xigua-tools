package org.xyx.redis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xyx.redis.cache.anno.CacheMultiKeys;
import org.xyx.redis.cache.anno.CacheSingleKey;

import java.util.List;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2021/1/23 下午10:04
 */

@Component
public class CacheProxy {

    private static final Logger logger = LoggerFactory.getLogger(CacheProxy.class);


    @CacheSingleKey(key = "xyx", type = CacheType.LOCAL)
    public String test() {
        logger.info("[CacheProxy] test in --------->");
        return "the value of key 'xyx'";
    }

    @CacheSingleKey(key = "xyx", type = CacheType.LOCAL)
    public CacheValue testObjLocal() {
        logger.info("[CacheProxy] testObjLocal in --------->");
        return new CacheValue(100, "xyx");
    }

    @CacheSingleKey(key = "xyx", type = CacheType.REDIS, result = CacheValue.class)
    public CacheValue testObjRedis() {
        logger.info("[CacheProxy] testObjRedis in --------->");
        return new CacheValue(100, "xyx");
    }


    @CacheSingleKey(key = "xyx_%s", type = CacheType.LOCAL)
    public String test(String v) {
        logger.info("[CacheProxy] test({}) in --------->", v);
        return String.format("the value of key 'xyx_%s'", v);
    }


    @CacheMultiKeys(key = "xyx_%s_%s", type = CacheType.LOCAL)
    public String test(String v, List<Integer> ids) {
        logger.info("[CacheProxy] test({}, {}) in --------->", v, ids);
        return String.format("the value of key 'xyx_%s_%s'", v, ids);
    }


    static class CacheValue {

        private Integer a;

        private String b;

        public CacheValue() {

        }

        public CacheValue(Integer a, String b) {
            this.a = a;
            this.b = b;
        }


        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return "CacheValue{" +
                    "a=" + a +
                    ", b='" + b + '\'' +
                    '}';
        }
    }

}
