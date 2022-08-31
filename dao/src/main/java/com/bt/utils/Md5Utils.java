package com.bt.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 14:49
 **/
public class Md5Utils {
    public static String md5(String data){
        try {
            MessageDigest md5=MessageDigest.getInstance("md5");

            md5.update(data.getBytes("utf-8"));

            byte[] digest = md5.digest();

            return  new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }
}
