package org.xyx.rest.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2020/12/29
 */
public class HeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate request) {
        request.method();
        request.path();
        request.url();

        // todo
    }

}
