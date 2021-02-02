package org.xyx.redis.lock;

import org.springframework.stereotype.Component;

/**
 * description here
 *
 * @author xueyongxin
 */

@Component
public class TryLockProxy {

    private void doBusiness() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @TryLock
    public void testLockKey() {
        System.out.println(String.format("[testLockKey] thread = %s, start <<<<<<<<========", Thread.currentThread().getName()));
        doBusiness();
        System.out.println(String.format("[testLockKey] thread = %s, end   ========>>>>>>>>", Thread.currentThread().getName()));
    }


    @TryLock(key = "xyx", rules = {"#edf"})
    public void testLockKey(Integer abc, String edf) {
        System.out.println(String.format("[testLockKey] thread = %s, abc=%s start <<<<<<<<========", Thread.currentThread().getName(), abc));
        doBusiness();
        System.out.println(String.format("[testLockKey] thread = %s, abc=%s end   ========>>>>>>>>", Thread.currentThread().getName(), abc));
    }


    @TryLock(key = "xyx", rules = {"#param.id", "#param.name"})
    public void testLockKey(LockParam param) {
        System.out.println(String.format("[testLockKey] thread = %s, param=%s start <<<<<<<<========", Thread.currentThread().getName(), param));
        doBusiness();
        System.out.println(String.format("[testLockKey] thread = %s, param=%s end   ========>>>>>>>>", Thread.currentThread().getName(), param));
    }


    @TryLock(key = "xyx", rules = {"#param.abc"})
    public void testLockNoSuchField(LockParam param) {
    }


    /**
     * 等3秒
     * */
    @TryLock(key = "xyx", waitTime = 3)
    public void testLockWait() {

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
        }

    }


    /**
     * 等4秒，保持3秒，处理5秒
     * */
    @TryLock(key = "xyx", holdTime = 3)
    public void testLockLease() {
        System.out.printf("%s  in:%s%n", Thread.currentThread().getName(), System.currentTimeMillis());
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
        }
        System.out.printf("%s out:%s%n", Thread.currentThread().getName(), System.currentTimeMillis());
    }

    @TryLock(key = "xyx", waitTime = 4)
    public void testLockLease2() {
        System.out.printf("%s  in:%s%n", Thread.currentThread().getName(), System.currentTimeMillis());
    }

}
