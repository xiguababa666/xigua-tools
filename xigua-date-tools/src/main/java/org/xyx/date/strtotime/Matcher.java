package org.xyx.date.strtotime;

import java.util.Date;

public enum Matcher {

    SECONDS("([-+]\\d+) seconds"),
    MINUTES("([-+]\\d+) minutes"),
    DAY("([-+]\\d+) minutes"),
    WEEK("([-+]\\d+) minutes"),
    MONTHS("([-+]\\d+) minutes"),
    YEARS("([-+]\\d+) minutes"),
    ;

    private String rule;

    Matcher(String rule) {
        this.rule = rule;
    }

    public Date strtotime() {
        return null;
    }
}
