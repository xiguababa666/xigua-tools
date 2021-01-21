package org.xyx.date.strtotime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description here
 *
 * @author xueyongxin
 * @date 2020/12/22
 */
public abstract class AbstractMather implements ITimeMatcher {

    protected Pattern numeric = Pattern.compile("[+-]{1}\\d+");

    /**
     *
     * description: TODO
     *
     * @return
     *
     * */
    protected abstract long getUnitMs();

    public long getMilliseconds(String timeString) {
        Matcher matcher = numeric.matcher(timeString);
        long ms = 0;
        while (matcher.find()) {
            ms += Integer.parseInt(matcher.group()) * getUnitMs();
        }
        return ms;
    }
}
