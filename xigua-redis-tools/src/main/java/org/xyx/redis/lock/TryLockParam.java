package org.xyx.redis.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来修饰入参，本来想通过参数名，但编译后参数名就变成了arg0,arg1，
 * 虽然加编译参数能保持参数名，但不太通用
 *
 * @author xueyongxin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface TryLockParam {
}
