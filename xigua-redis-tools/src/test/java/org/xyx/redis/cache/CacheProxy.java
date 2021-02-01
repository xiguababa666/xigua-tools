package org.xyx.redis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xyx.redis.cache.anno.CacheSingleKey;

import java.util.ArrayList;
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


    @CacheSingleKey(type = CacheType.LOCAL)
    public CacheValue testObjLocal() {
        logger.info("[CacheProxy] testObjLocal in --------->");
        return new CacheValue(100, "xyx");
    }

    @CacheSingleKey
    public CacheValue testObjRedis() {
        logger.info("[CacheProxy] testObjRedis in --------->");
        return new CacheValue(100, "xyx");
    }


    @CacheSingleKey(type = CacheType.LOCAL, elementType = CacheValue.class)
    public List<CacheValue> testListObjLocal() {
        logger.info("[CacheProxy] testObjRedis in --------->");
        List<CacheValue> list = new ArrayList<>();
        list.add(new CacheValue(100, "xyx"));
        list.add(new CacheValue(101, "xyx1"));
        list.add(new CacheValue(102, "xyx2"));
        return list;
    }


    @CacheSingleKey(elementType = CacheValue.class)
    public List<CacheValue> testListObjRedis() {
        logger.info("[CacheProxy] testObjRedis in --------->");
        List<CacheValue> list = new ArrayList<>();
        list.add(new CacheValue(100, "xyx"));
        list.add(new CacheValue(101, "xyx1"));
        list.add(new CacheValue(102, "xyx2"));
        return list;
    }


    @CacheSingleKey(elementType = CacheValue.class)
    public List<CacheValue> testListObjRedis1(Integer a, String b) {
        logger.info("[CacheProxy] testObjRedis in --------->");
        List<CacheValue> list = new ArrayList<>();
        list.add(new CacheValue(100, "xyx"));
        list.add(new CacheValue(101, "xyx1"));
        list.add(new CacheValue(102, "xyx2"));
        return list;
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
