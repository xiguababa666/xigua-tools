package org.xyx.rest.feign.anno;

import feign.RequestInterceptor;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 *
 * @author xueyongxin
 * @date 2020/12/29
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RestfulService {

    /**
     *
     * restful 配置key
     *
     */
    @AliasFor("value")
    String server() default "";

    @AliasFor("server")
    String value() default "";

    /**
     *
     * 自定义拦截器
     * */
    Class<? extends RequestInterceptor>[] interceptor() default {};
}
