package org.xyx.date.strtotime;

public class MatcherMinutes extends AbstractMather {

    @Override
    protected long getUnitMilliseconds() {
        return 60 * 1000;
    }

    @Override
    public long getMilliseconds(String timeString) {
        return 0;
    }
}
