package org.xyx.rest.feign.properties;


/**
 * description
 *
 * @author xueyongxin
 * @date 2020/12/29
 */

public class RemoteServerConfig {

    /**
     * 域名
     * */
    private String host;

    /**
     * 签名key
     * */
    private String appKey;

    /**
     * 签名密钥
     * */
    private String appSecret;

    private int connectTimeout = 3;

    private int readTimeout = 3;

    /** 重试间隔 **/
    private int retryPeriod = 3;

    /** 最多重试次数 **/
    private int retries = 2;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getRetryPeriod() {
        return retryPeriod;
    }

    public void setRetryPeriod(int retryPeriod) {
        this.retryPeriod = retryPeriod;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }
}
