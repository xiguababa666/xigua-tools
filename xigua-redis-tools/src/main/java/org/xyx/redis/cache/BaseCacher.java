package org.xyx.redis.cache;


import java.util.Random;

/**
 * description here
 *
 * @author xueyongxin
 */
public abstract class BaseCacher implements Cacher {

    /**
     * 生成随机过期时间，防止集中过期
     *
     * @param time 过期时间 秒
     * @return 秒
     * */
    protected static long generateTimeOffset(long time) {

        // 随机增减5%内，拍脑袋拍的
        int range = (int) (time * 0.05);
        int offset = new Random().nextInt(range);
        if (offset % 2 != 0) {
            offset = -offset;
        }
        return time + offset;
    }


}
