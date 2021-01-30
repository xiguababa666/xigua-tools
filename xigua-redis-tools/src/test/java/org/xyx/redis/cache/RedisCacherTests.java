package org.xyx.redis.cache;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xyx.redis.Application;
import org.xyx.redis.cache.redis.RedisCacher;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description here
 *
 * @author xueyongxin
 */

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class RedisCacherTests {

    @Resource
    private RedisCacher redisCacher;


    @Test
    public void test() {
        List<String> keys = Lists.newArrayList("xyx3", "a", "b", "xyx1", "c", "xyx2");
        Map<String, Object> values = redisCacher.get(keys);
        System.out.println(values);
    }


}
