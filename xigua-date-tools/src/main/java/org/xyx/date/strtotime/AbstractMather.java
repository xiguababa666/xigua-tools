package org.xyx.date.strtotime;

import java.util.regex.Pattern;

public abstract class AbstractMather implements ITimeMatcherMs {

    protected Pattern pattern;

    protected void init(Pattern pattern) {
        this.pattern = pattern;
    }

    protected long getUnitMilliseconds() {
        return 0;
    }

}
