package org.xyx.rest.retrofit;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * rest 请求 日志
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */

@Slf4j
public class RequestInterceptor implements Interceptor {

    RequestInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = "";
        HttpUrl url = null;
        if (request != null) {
            method = request.method();
            url = request.url();
        }
        log.info("HTTP METHOD:{}, REQUEST:{}", method, url);
        return chain.proceed(request);
    }
}
