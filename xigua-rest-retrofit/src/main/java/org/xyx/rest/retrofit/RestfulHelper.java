package org.xyx.rest.retrofit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * description
 *
 * @author xyx
 * @date 2018/10/27 18:20
 */
public class RestfulHelper {

    private static final int HTTP_OK = 200;

    private RestfulHelper() {

    }

    public static <T> T getResponse(Call<T> call) throws Exception {
        Response<T> response = call.execute();
        int code = response.code();
        if (code != HTTP_OK) {
            throw new IllegalStateException(String.format("Http status[code:%s] is not Ok!", code));
        }
        return response.body();
    }

}
