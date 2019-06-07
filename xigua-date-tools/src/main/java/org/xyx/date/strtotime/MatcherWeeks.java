package org.xyx.date.strtotime;

public class MatcherWeeks extends AbstractMather {

    @Override
    protected long getUnitMilliseconds() {
        return 7 * 24 * 60 * 60 * 1000;
    }

    @Override
    public long getMilliseconds(String timeString) {
        return 0;
    }
}
