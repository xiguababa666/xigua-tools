package org.xyx.rest.retrofit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * description
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // add common header here

        return chain.proceed(request);
    }
}
