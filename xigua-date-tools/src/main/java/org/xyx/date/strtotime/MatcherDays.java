package org.xyx.date.strtotime;

public class MatcherDays extends AbstractMather {

    @Override
    protected long getUnitMilliseconds() {
        return 24 * 60 * 60 * 1000;
    }

    @Override
    public long getMilliseconds(String timeString) {
        return 0;
    }
}
