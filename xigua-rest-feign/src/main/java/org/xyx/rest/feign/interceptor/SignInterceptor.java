package org.xyx.rest.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2020/12/29
 */
public class SignInterceptor implements RequestInterceptor {

    private String appKey;

    private String appSecret;

    public SignInterceptor(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    @Override
    public void apply(RequestTemplate request) {
        request.method();
        request.path();
        request.url();

        // todo
    }

}
