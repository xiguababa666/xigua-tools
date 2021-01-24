package org.xyx.redis.cache;

/**
 *
 * @author xueyongxin
 */
public class CacheDataException extends RuntimeException {

    private Integer code;

    public CacheDataException() {
        super();
    }

    public CacheDataException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CacheDataException(String message) {
        super(message);
    }

    public CacheDataException(Throwable t) {
        super(t);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
