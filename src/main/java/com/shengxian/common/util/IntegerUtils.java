package com.shengxian.common.util;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-01-09
 * @Version: 1.0
 */
public class IntegerUtils {

    public static boolean isEmpty(Integer s){
        return s == null || s == 0;
    }

    public static boolean isNotEmpty(Integer s){
        return s != null || s != 0;
    }

    public static boolean Empty(Integer s){
        return s != null && s != 0;
    }
}
