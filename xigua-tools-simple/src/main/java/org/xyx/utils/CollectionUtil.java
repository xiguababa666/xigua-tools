package org.xyx.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * description here
 *
 * @author xueyongxin
 */
public class CollectionUtil {

    private CollectionUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * 差集，在a中不在b中
     *
     * @param a
     * @param b
     * @return List not in 'b'
     */
    public static <T> List<T> except(Collection<T> a, Collection<T> b) {
        if (a == null) a = Collections.emptyList();
        if (b == null) b = Collections.emptyList();
        List<T> aExceptB = new ArrayList<>(a);
        aExceptB.removeAll(b);
        return aExceptB;
    }


    /**
     * 交集
     *
     * @param a
     * @param b
     * @return
     */
    public static <T> List<T> intersect(Collection<T> a, Collection<T> b) {
        if (a == null) a = Collections.emptyList();
        if (b == null) b = Collections.emptyList();
        List<T> intersection = new ArrayList<>(a);
        intersection.retainAll(b);
        return intersection;
    }


}
