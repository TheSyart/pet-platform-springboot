package com.pets.utils.base;

/**
 * @author davi
 */
public class BaseErrorException extends RuntimeException {

    private final static int C_400 = 400;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;


    public static BaseErrorException error400(String msg) {
        return new BaseErrorException(msg);
    }

    public BaseErrorException(String msg) {
        super(msg);
        this.code = C_400;
        this.msg = msg;
    }

    public BaseErrorException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

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
}
