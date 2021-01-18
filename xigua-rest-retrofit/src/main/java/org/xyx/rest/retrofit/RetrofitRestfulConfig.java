package org.xyx.rest.retrofit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * description
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */

@Getter
@Setter
@ToString
public class RetrofitRestfulConfig {

    /**
     * 域名
     * */
    private String domain;

    /**
     * 签名key
     * */
    private String appKey;

    /**
     * 签名密钥
     * */
    private String appSecret;

    private int connectTimeout = 30;

    private int writeTimeout = 30;

    private int readTimeout = 30;

}
