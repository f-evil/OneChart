package com.easy.onechartview.view;

/**
 * @author fuyujie
 * @package: com.easy.customview.test8
 * @fileNmae StringUtils
 * @date 2019/10/29 11:50
 * @describe
 * @org easylinking
 * @email f279259625@gmail.com
 */
public class StringUtils {


    public static String removeEmpty(String text) {

        String temp;
        if (text == null || text.equals("null")) {
            temp = "";
        } else {
            temp = text;
        }
        return temp;
    }

    public static boolean isNoEmpey(String text) {
        return text != null && !text.isEmpty();
    }
}
