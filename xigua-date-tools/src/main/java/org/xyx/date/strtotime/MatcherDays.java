package org.xyx.date.strtotime;

/**
 * description here
 *
 * @author xueyongxin
 * @date 2020/12/22
 */
public class MatcherDays extends AbstractMather {

    @Override
    protected long getUnitMs() {
        return 24 * 60 * 60 * 1000;
    }

}
