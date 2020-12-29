package org.xyx.date.strtotime;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2020/12/22
 */
public class MatcherMinutes extends AbstractMather {

    @Override
    protected long getUnitMs() {
        return 60 * 1000;
    }

}
