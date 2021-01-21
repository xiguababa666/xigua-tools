package org.xyx.date.strtotime;

import java.util.Date;
import java.util.List;

/**
 * description here
 *
 * @author xueyongxin
 * @date 2020/12/22
 */
public final class StrToTime {

    private StrToTime() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * 类似PHP的strtotime，弱化版
     * 例  StrToTime.str2time("+2 days -10 hours");
     *
     * @param timeString
     * @return
     *
     * */
    public static Date str2time(String timeString) {
        List<TimeRule> rules = TimeMatcher.getMatcher(timeString);
        long total = 0;
        for (TimeRule rule : rules) {
            total += rule.getTimeMatcher().getMilliseconds(rule.getRule());
        }
        return new Date(System.currentTimeMillis() + total);
    }


}
