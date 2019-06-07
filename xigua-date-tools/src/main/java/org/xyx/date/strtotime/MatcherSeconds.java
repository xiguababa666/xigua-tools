package org.xyx.date.strtotime;

public class MatcherSeconds extends AbstractMather {

    @Override
    protected long getUnitMilliseconds() {
        return 1000;
    }

    @Override
    public long getMilliseconds(String timeString) {
        return 0;
    }
}
