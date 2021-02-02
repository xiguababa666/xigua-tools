package org.xyx.redis.lock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xyx.redis.Application;

import javax.annotation.Resource;

/**
 * description here
 *
 * @author xueyongxin
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class TryLockTests {

    private final static Logger logger = LoggerFactory.getLogger(TryLockTests.class);

    @Resource
    private TryLockProxy needLockMethods;


    private void sleep(long interval) {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testLockKey() {
        new Thread(() -> needLockMethods.testLockKey()).start();
        sleep(200);
        try {
            needLockMethods.testLockKey();
            Assert.fail();
        } catch (DistributedLockerException e) {
            logger.info("", e);
            Assert.assertTrue(true);
        }
        sleep(900);
    }



    @Test
    public void testLockKey2() {
        new Thread(() -> needLockMethods.testLockKey(123, "hhh")).start();
        sleep(200);
        try {
            needLockMethods.testLockKey(123, "hhh");
            Assert.fail();
        } catch (DistributedLockerException e) {
            logger.info("", e);
            Assert.assertTrue(true);
        }
        sleep(900);
    }



    @Test
    public void testLockKey3() {

        LockParam p = new LockParam();
        p.setId(1);
        p.setName("xueyongxin");

        new Thread(() -> needLockMethods.testLockKey(p)).start();
        sleep(200);
        try {
            needLockMethods.testLockKey(p);
            Assert.fail();
        } catch (DistributedLockerException e) {
            logger.info("", e);
            Assert.assertTrue(true);
        }
        sleep(900);
    }


    @Test
    public void testLockParamCheckFail() {
        try {
            needLockMethods.testLockNoSuchField(new LockParam());
            Assert.fail();
        } catch (Exception e) {
            logger.info("", e);
            Assert.assertTrue(true);
        }

    }


    @Test
    public void testLockWait() {
        new Thread(() -> needLockMethods.testLockWait()).start();
        sleep(20);
        long start = System.currentTimeMillis();
        try {
            needLockMethods.testLockWait();
            Assert.fail();
        } catch (DistributedLockerException e) {
            long end = System.currentTimeMillis();
            Assert.assertTrue((end - start) >= 3000);
            Assert.assertTrue((end - start) < 3020);
            System.out.println(end - start);
            logger.info("", e);
            Assert.assertTrue(true);
        }
        sleep(1000);
    }


    @Test
    public void testLockLease() {
        new Thread(() -> needLockMethods.testLockLease()).start();
        sleep(20);
        try {
            needLockMethods.testLockLease2();
            Assert.assertTrue(true);
        } catch (Exception e) {
            logger.info("", e);
            Assert.fail();
        }
        sleep(1000);
    }


}
