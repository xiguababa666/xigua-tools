package org.xyx.date.strtotime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TimeMatcher implements ITimeMatcherMs {

    UNSUPPORTED("", new MatcherUnsupported()),
    SECONDS("([-+]\\d+) seconds", new MatcherSeconds()),
    MINUTES("([-+]\\d+) minutes", new MatcherMinutes()),
    HOURS("([-+]\\d+) hours", new MatcherHours()),
    DAY("([-+]\\d+) days", new MatcherDays()),
    WEEK("([-+]\\d+) weeks", new MatcherDays()),
    MONTHS("([-+]\\d+) months", new MatcherMonths()),
    YEARS("([-+]\\d+) years", new MatcherYears()),
    ;

    private final String rule;
    private final Pattern pattern;
    private final AbstractMather matcherMs;

    TimeMatcher(String rule, AbstractMather matcherMs) {
        this.rule = rule;
        this.pattern = Pattern.compile(rule);
        this.matcherMs = matcherMs;
        this.matcherMs.init(this.pattern);
    }


    public long getMilliseconds(String timeString) {

        return matcherMs.getMilliseconds(timeString);
    }

    public static TimeMatcher getMatcher(String timeString) {

        TimeMatcher[] enums = TimeMatcher.values();
        for (TimeMatcher e : enums) {
            Pattern pattern = e.pattern;
            Matcher matcher = pattern.matcher(timeString);

            if (matcher.find()) {
                return e;
            }

        }

        return UNSUPPORTED;

    }

}
