package org.xyx.rest.retrofit;

import okhttp3.Interceptor;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RestfulService {

    /**
     *
     * restful 配置key 详见{@see org.xyx.rest.retrofit.RetrofitRestfulConfig yourServerName}
     *
     */
    @AliasFor("value")
    String key() default "";

    @AliasFor("key")
    String value() default "";

    /**
     *
     * 自定义拦截器
     * */
    Class<? extends Interceptor>[] interceptor() default {};
}
