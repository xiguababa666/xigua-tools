package org.xyx.redis.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xyx.redis.Application;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2021/1/23 下午10:02
 */

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class CacheTests {


    private static final Logger logger = LoggerFactory.getLogger(CacheTests.class);


    @Resource
    private CacheProxy cacheProxy;


    @Test
    public void testObjLocal() {
        CacheProxy.CacheValue s = cacheProxy.testObjLocal();
        logger.info("[====LocalCacheTests====] testObjLocal 1 get value = {}", s);

        s = cacheProxy.testObjLocal();
        logger.info("[====LocalCacheTests====] testObjLocal 2 get value = {}", s);
    }

    @Test
    public void testObjRedis() {
        CacheProxy.CacheValue[] s = cacheProxy.testObjRedis();
        logger.info("[====LocalCacheTests====] testObjRedis 1 get value = {}", s);

        s = cacheProxy.testObjRedis();
        logger.info("[====LocalCacheTests====] testObjRedis 2 get value = {}", s);
    }

    @Test
    public void testObjRedis2() {
        CacheProxy.CacheValue s = cacheProxy.testObjRedis2();
        logger.info("[====LocalCacheTests====] testObjRedis2 1 get value = {}", s);

        s = cacheProxy.testObjRedis2();
        logger.info("[====LocalCacheTests====] testObjRedis2 2 get value = {}", s);
    }


    @Test
    public void testObjRedis3() {
//        BigDecimal s = cacheProxy.testObjRedis3();
        Boolean s = cacheProxy.testObjRedis3();
        logger.info("[====LocalCacheTests====] testObjRedis3 1 get value = {}", s);

        s = cacheProxy.testObjRedis3();
        logger.info("[====LocalCacheTests====] testObjRedis3 2 get value = {}", s);
    }


    @Test
    public void testObjRedisNull() {
        String s = cacheProxy.testObjRedisNull();
        logger.info("[====LocalCacheTests====] testObjRedis3 1 get value = {}", s);

        s = cacheProxy.testObjRedisNull();
        logger.info("[====LocalCacheTests====] testObjRedis3 2 get value = {}", s);
    }


    @Test
    public void testListObjLocal() {
        List<CacheProxy.CacheValue> s = cacheProxy.testListObjLocal();
        logger.info("[====LocalCacheTests====] testListObjLocal 1 get value = {}", s);

        s = cacheProxy.testListObjLocal();
        logger.info("[====LocalCacheTests====] testListObjLocal 2 get value = {}", s);
    }


    @Test
    public void testListObjRedis() {
        List<CacheProxy.CacheValue> s = cacheProxy.testListObjRedis();
        logger.info("[====LocalCacheTests====] testListObjRedis 1 get value = {}", s);

        s = cacheProxy.testListObjRedis();
        logger.info("[====LocalCacheTests====] testListObjRedis 2 get value = {}", s);
    }


    @Test
    public void testListObjRedis1() {
        List<CacheProxy.CacheValue> s = cacheProxy.testListObjRedis1(1, "abc");
        logger.info("[====LocalCacheTests====] testListObjRedis1 1 get value = {}", s);

        s = cacheProxy.testListObjRedis1(1, "abc");
        logger.info("[====LocalCacheTests====] testListObjRedis1 2 get value = {}", s);

        s = cacheProxy.testListObjRedis1(2, "abc");
        logger.info("[====LocalCacheTests====] testListObjRedis1 2 get value = {}", s);
    }


    @Test
    public void testSpringCacheable() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(4);
        CacheProxy.CacheValue s = cacheProxy.testSpringCacheable2(1, "abc");
        logger.info("[====testSpringCacheable====] {}", s);

    }


}
