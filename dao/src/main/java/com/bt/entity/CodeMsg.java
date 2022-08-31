package com.bt.entity;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 11:23
 **/
public class CodeMsg {
    private int code;
    private String message;
    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private CodeMsg(){};

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
//    通用的错误消息
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
//    地址管理
    public static CodeMsg NAME_NOT_EXIST = new CodeMsg(500101, "收件人不能为空");
    public static CodeMsg PHONE_NOT_EXIST = new CodeMsg(500102, "手机号不能为空");
    public static CodeMsg ADDRESS_NOT_EXIST = new CodeMsg(500103, "地址不能为空");
}
