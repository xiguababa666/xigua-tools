package org.xyx.rest.retrofit;

import java.lang.annotation.*;

/**
 * 待解决问题，怎么统一加header，统一签名
 *
 * @author xyx
 * @date 2018/9/14 15:24
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RestService {
    /**
     * base url
     *
     */
    String value() default "";
}
