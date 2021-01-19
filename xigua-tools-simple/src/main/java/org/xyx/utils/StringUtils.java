package org.xyx.utils;


/**
 *
 * @author xueyongxin
 */
public final class StringUtils {


    private StringUtils() {
        throw new UnsupportedOperationException();
    }


    public static String trim(String str, char ch) {

        if (isEmpty(str)) return str;

        char[] chars = str.toCharArray();
        int start = 0, end = chars.length - 1;
        while (start <= end && chars[start] == ch) start++;
        if (start > end) return "";
        while (end >= 0 && chars[end] == ch) end--;
        return str.substring(start, end + 1);
    }


    public static boolean isEmail(String str) {
        return false;
    }


    public static boolean isPhoneNumber(String str) {
        return false;
    }


    public static boolean isIdNumber(String str) {
        return false;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isBlank(CharSequence cs) {
        if (isEmpty(cs)) {
            return true;
        }
        for(int i = 0; i < cs.length(); ++i) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int subStrCount(String str, String find) {
        if (isEmpty(str) || isEmpty(find)) {
            return 0;
        }

        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(find, idx)) != -1) {
            idx++;
            count++;
        }

        return count;
    }



    /**
     * kmp next数组
     * */
    public static int[] next(String str) {
        int j, k;
        int[] next = new int[str.length()];
        j = 0;
        k = -1;
        next[0] = -1;
        while (j < str.length() - 1) {
            if (k == -1 || str.charAt(j) == str.charAt(k)) {
                j++;
                k++;
                if (str.charAt(j) != str.charAt(k)) {
                    next[j] = k;
                } else {
                    next[j] = next[k];
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }

    /**
     * kmp算法
     * */
    public static int kmp(String str, String find) {
        int[] next = next(find);
        int i = 0, j = 0, v;
        while (i < str.length() && j < find.length()) {
            if (j == -1 || str.charAt(i) == find.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j >= find.length()) {
            v = i - find.length();
        } else {
            v = -1;
        }
        return v;
    }

    /**
     * 可判断小数
     * */
    public static boolean isNumber(String cs) {
        return !isEmpty(cs) && cs.matches("-?[0-9]+.?[0-9]*");
    }


}
