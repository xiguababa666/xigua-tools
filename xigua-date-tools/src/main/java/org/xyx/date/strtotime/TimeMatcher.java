package org.xyx.date.strtotime;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2020/12/22
 */
public enum TimeMatcher implements ITimeMatcher {

    SECONDS("([-+]{1}\\d+) seconds", new MatcherSeconds()),
    MINUTES("([-+]{1}\\d+) minutes", new MatcherMinutes()),
    HOURS("([-+]{1}\\d+) hours", new MatcherHours()),
    DAY("([-+]{1}\\d+) days", new MatcherDays()),
    WEEK("([-+]{1}\\d+) weeks", new MatcherWeeks()),
    MONTHS("([-+]{1}\\d+) months", new MatcherMonths()),
    YEARS("([-+]{1}\\d+) years", new MatcherYears()),
    ;

    private final Pattern pattern;
    private final AbstractMather matcherMs;

    TimeMatcher(String rule, AbstractMather matcherMs) {
        this.pattern = Pattern.compile(rule);
        this.matcherMs = matcherMs;
    }


    public long getMilliseconds(String timeString) {
        return matcherMs.getMilliseconds(timeString);
    }

    protected static List<TimeRule> getMatcher(String timeString) {

        List<TimeRule> ret = new LinkedList<>();
        TimeMatcher[] enums = TimeMatcher.values();
        for (TimeMatcher e : enums) {
            Matcher matcher = e.pattern.matcher(timeString);
            while (matcher.find()) {
                ret.add(new TimeRule(e, matcher.group()));
            }
        }
        return ret;
    }

}
