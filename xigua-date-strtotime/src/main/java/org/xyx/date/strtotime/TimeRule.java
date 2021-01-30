package org.xyx.date.strtotime;

/**
 * description here
 *
 * @author xueyongxin
 * @date 2020/12/22
 */
public class TimeRule {

    private TimeMatcher timeMatcher;

    private String rule;

    public TimeRule(TimeMatcher timeMatcher, String rule) {
        this.timeMatcher = timeMatcher;
        this.rule = rule;
    }

    public TimeMatcher getTimeMatcher() {
        return timeMatcher;
    }

    public String getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return "TimeRule{" +
                "timeMatcher=" + timeMatcher +
                ", rule='" + rule + '\'' +
                '}';
    }
}
