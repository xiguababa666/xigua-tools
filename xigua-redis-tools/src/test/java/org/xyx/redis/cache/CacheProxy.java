package org.xyx.redis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.xyx.redis.cache.anno.CacheData;
import org.xyx.redis.cache.anno.CacheSingleKey;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
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

    @CacheData(name = "abc",
            type = CacheType.LOCAL,
            keys = {"#a", "#b"})
    public String testCache1(String a, Integer b) {
        // do query
        return "xyx";
    }

    // 默认redis
    @CacheData(name = "abc",
            keys = {"#a"},
            elementType = CacheValue.class)
    public List<CacheValue> testCache2(String a, Integer b) {
        // do query
        List<CacheValue> ret = new LinkedList<>();
        ret.add(new CacheValue(100, "xyx"));
        return ret;
    }

    @CacheData(name = "abc",
            keys = {"#ids"},
            elementType = CacheValue.class)
    public List<CacheValue> testCache3(List<Integer> ids) {
        // do query
        List<CacheValue> ret = new ArrayList<>();
        for (Integer id : ids) {
            ret.add(new CacheValue(id, "xyx_" + id));
        }
        return ret;
    }


    @CacheSingleKey(type = CacheType.LOCAL)
    public CacheValue testObjLocal() {
        logger.info("[CacheProxy] testObjLocal in --------->");
        return new CacheValue(100, "xyx");
    }

    @CacheSingleKey(type = CacheType.LOCAL)
    public int[] testObjLocal2() {
        logger.info("[CacheProxy] testObjLocal2 in --------->");
        return new int[]{1};
    }

    @CacheSingleKey
    public CacheValue[] testObjRedis() {
        logger.info("[CacheProxy] testObjRedis in --------->");
        return new CacheValue[] {new CacheValue(100, "xyx"), new CacheValue(111, "aaa")};
    }


    @CacheSingleKey
    public CacheValue testObjRedis2() {
        logger.info("[CacheProxy] testObjRedis2 in --------->");
        return new CacheValue(100, "xyx");
    }


    @CacheSingleKey
//    public BigDecimal testObjRedis3() {
    public Boolean testObjRedis3() {
        logger.info("[CacheProxy] testObjRedis2 in --------->");
//        return new BigDecimal("1.111111111");
        return true;
    }


    @CacheSingleKey
    public String testObjRedisNull() {
        logger.info("[CacheProxy] testObjRedisNull in --------->");
        return null;
    }


    @CacheSingleKey(type = CacheType.LOCAL, elementType = CacheValue.class)
    public List<CacheValue> testListObjLocal() {
        logger.info("[CacheProxy] testListObjLocal in --------->");
        List<CacheValue> list = new ArrayList<>();
        list.add(new CacheValue(100, "xyx"));
        list.add(new CacheValue(101, "xyx1"));
        list.add(new CacheValue(102, "xyx2"));
        return list;
    }


    @CacheSingleKey(elementType = CacheValue.class)
    public List<CacheValue> testListObjRedis() {
        logger.info("[CacheProxy] testListObjRedis in --------->");
        List<CacheValue> list = new ArrayList<>();
        list.add(new CacheValue(100, "xyx"));
        list.add(new CacheValue(101, "xyx1"));
        list.add(new CacheValue(102, "xyx2"));
        return list;
    }


    @CacheSingleKey(elementType = CacheValue.class)
    public List<CacheValue> testListObjRedis1(Integer a, String b) {
        logger.info("[CacheProxy] testListObjRedis1 in --------->");
        List<CacheValue> list = new ArrayList<>();
        list.add(new CacheValue(100, "xyx"));
        list.add(new CacheValue(101, "xyx1"));
        list.add(new CacheValue(102, "xyx2"));
        return list;
    }



    @Cacheable(cacheNames = "xyx_test", key = "#p0")
    public List<CacheValue> testSpringCacheable(List<Integer> ids) {
        List<CacheValue> ret = new ArrayList<>();
        for (Integer id : ids) {
            ret.add(new CacheValue(id, "b_" + id));
        }
        logger.info("[---------------->] not cached, ids:{}", ids);
        return ret;
    }

    @Cacheable(cacheNames = "xyx_test", key = "#a")
    public CacheValue testSpringCacheable2(Integer a, String b) {
        return new CacheValue(1, "b_" + 1);
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
