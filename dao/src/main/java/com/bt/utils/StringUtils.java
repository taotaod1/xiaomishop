package com.bt.utils;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 11:48
 **/
public class StringUtils {
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str.trim())){
            return true;
        }
        return false;
    }
}
