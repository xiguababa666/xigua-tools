package org.xyx.date.strtotime;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2020/12/22
 */
public interface ITimeMatcher {

    /**
     *
     * @param timeString like "+1 days"/"-10 minutes"
     * @return long milliseconds
     *
     * */
    long getMilliseconds(String timeString);

}
