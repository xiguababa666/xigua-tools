package org.xyx.date.strtotime;

/**
 * description here
 *
 * @author xueyongxin
 * @date 2020/12/22
 */
public class MatcherHours extends AbstractMather {

    @Override
    protected long getUnitMs() {
        return 60 * 60 * 1000;
    }

}
