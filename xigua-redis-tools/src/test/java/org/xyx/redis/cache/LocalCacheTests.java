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
public class LocalCacheTests {


    private static final Logger logger = LoggerFactory.getLogger(LocalCacheTests.class);


    @Resource
    private CacheProxy cacheProxy;


    @Test
    public void test1() {
        String s = cacheProxy.test();
        logger.info("[====LocalCacheTests====] 1 get value = {}", s);

        s = cacheProxy.test();
        logger.info("[====LocalCacheTests====] 2 get value = {}", s);
    }


    @Test
    public void test2() {
        String s = cacheProxy.test("abc");
        logger.info("[====LocalCacheTests====] 1 get value = {}", s);

//        s = cacheProxy.test("abc");
//        logger.info("[====LocalCacheTests====] 2 get value = {}", s);
    }


    @Test
    public void test3() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        String s = cacheProxy.test("abc", ids);
        logger.info("[====LocalCacheTests====] 1 get value = {}", s);

//        s = cacheProxy.test("abc");
//        logger.info("[====LocalCacheTests====] 2 get value = {}", s);
    }


}
