package org.xyx.tools.demo.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LockTestMain {

    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");

        LockTest t = (LockTest) app.getBean("lockTest");

        t.test();

        t.test2();

    }

}
