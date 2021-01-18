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
        String method = request.method();
        String path = request.path();
        String url = request.url();

        System.out.println("[HeaderInterceptor] method:" + method);
        System.out.println("[HeaderInterceptor]   path:" + path);
        System.out.println("[HeaderInterceptor]    url:" + url);
        // todo
    }

}
