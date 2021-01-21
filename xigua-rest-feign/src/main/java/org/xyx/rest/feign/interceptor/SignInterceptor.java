package org.xyx.rest.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * description here
 *
 * @author xueyongxin
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
        String method = request.method();
        String path = request.path();
        String url = request.url();

        System.out.println("[HeaderInterceptor] method:" + method);
        System.out.println("[HeaderInterceptor]   path:" + path);
        System.out.println("[HeaderInterceptor]    url:" + url);
        // todo
    }

}
