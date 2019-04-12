package org.xyx.date.strtotime;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinutesMatcher implements RuleMatcher {

    private static final Pattern minutes = Pattern.compile("([-+]\\d+) minutes");

    @Override
    public Date str2time() {

        return null;
    }

    public static void main(String[] args) {

        String s = "+1 minutes +1 minutes +2 minutes";

        Matcher m = minutes.matcher(s);
        if (m.find()) {
            System.out.println(m.group(1));
        }
    }
}
