package org.xyx.redis.lock;

/**
 *
 * @author xueyongxin
 */
public class DistributedLockerException extends RuntimeException {

    private Integer code;

    public DistributedLockerException() {
        super();
    }

    public DistributedLockerException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public DistributedLockerException(String message) {
        super(message);
    }

    public DistributedLockerException(Throwable t) {
        super(t);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
