package org.xyx.retrofit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * rest 请求 日志
 *
 * @author xyx
 * @date 2018/9/14 11:51
 */
public class RestLogInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(RestLogInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logger.info("HTTP REQUEST:{}, HEADER: {}", request.url(), request.headers());
        return chain.proceed(request);
    }
}
