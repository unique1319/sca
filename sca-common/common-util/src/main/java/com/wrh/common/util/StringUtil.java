package com.wrh.common.util;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/14 11:18
 * @describe
 */
public class StringUtil {

    /**
     * @description : 数字前补0，例如003,030等等
     */
    public static String frontCompWithZore(int number, int formatLength) {
        return String.format("%0" + formatLength + "d", number);
    }

}
