package com.bt.entity;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 11:20
 **/
public class Result<T> {
    private  int code;
    private String msg;
    private T data;
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    private Result(){};

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T>Result<T> success(T data) {
        return new Result<T>(0, "success", data);
    }
    public static <T>Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg.getCode(), codeMsg.getMessage(), null);
    }
}
