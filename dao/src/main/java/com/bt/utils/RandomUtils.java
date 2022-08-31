package com.bt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 14:40
 **/
public class RandomUtils {
//    生成一个激活码
   public static String createCode(){
       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSS");
       return sdf.format(new Date())+Integer.toHexString(new Random().nextInt(999999));
   }
//   生产订单号
    public static String createOrderCode(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date())+Integer.toHexString(new Random().nextInt(9999));
    }
}
