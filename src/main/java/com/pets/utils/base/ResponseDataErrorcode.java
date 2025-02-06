package com.pets.utils.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 接口请求与响应
 *
 */
public class ResponseDataErrorcode<T> implements Serializable {

    private static final long serialVersionUID = 8125672939123850928L;

    public static final int STATUS_CODE_200 = 200;
    public static final int STATUS_CODE_400 = 400;
    public static final int STATUS_CODE_401 = 401; //未登录

    public static final int STATUS_CODE_403 = 403; // 没有权限
    public static final int STATUS_CODE_404 = 404;
    public static final int STATUS_CODE_500 = 500;

    public static final int STATUS_CODE_410 = 410; // Gone
    public static final int STATUS_CODE_412 = 412; // 未满足前提条件
    public static final int STATUS_CODE_422 = 422; // 请求格式正确，但是由于含有语义错误，无法响应。
    public static final int STATUS_CODE_423 = 423; // 当前资源被锁定。

    public static final int STATUS_CODE_461 = 461; // 验证码错误

    public static final int STATUS_CODE_706 = 706; // 需要先获取token

    public static final String STATUS_MESSAGE_200 = "OK";
    public static final String STATUS_MESSAGE_400 = "Error";

    public static boolean isSuccess(ResponseDataErrorcode data){
        return data.errorcode == STATUS_CODE_200;
    }

    public static ResponseDataErrorcode OK() {
        return new ResponseDataErrorcode(STATUS_CODE_200, STATUS_MESSAGE_200);
    }

    public static <T> ResponseDataErrorcode OK(T data) {
        return new ResponseDataErrorcode(STATUS_CODE_200, STATUS_MESSAGE_200, data);
    }

    public static ResponseDataErrorcode ERROR(int code) {
        return new ResponseDataErrorcode(code, STATUS_MESSAGE_400);
    }

    public static <T> ResponseDataErrorcode ERROR(T data) {
        return new ResponseDataErrorcode(STATUS_CODE_400, STATUS_MESSAGE_400, data);
    }

    public static ResponseDataErrorcode ERROR(int code, String message) {
        return new ResponseDataErrorcode(code, message);
    }

    public static ResponseDataErrorcode ERROR(String message) {
        return new ResponseDataErrorcode(STATUS_CODE_400, message);
    }

    public static ResponseDataErrorcode ERROR() {
        return new ResponseDataErrorcode(STATUS_CODE_400, STATUS_MESSAGE_400);
    }


    public static <T> ResponseDataErrorcode ajaxReturn(int code, String message) {
        return new ResponseDataErrorcode(code, message);
    }
    public static <T> ResponseDataErrorcode ajaxReturn(int code, String message, T data) {
        return new ResponseDataErrorcode(code, message, data);
    }

    private int errorcode;

    private String message;

    private T data;

    public ResponseDataErrorcode() {
        errorcode = STATUS_CODE_200;
        message = STATUS_MESSAGE_200;
    }

    public ResponseDataErrorcode(int errorcode, String message) {
        this.errorcode = errorcode;
        this.message = message;
    }

    public ResponseDataErrorcode(int errorcode, String message, T data) {
        this.errorcode = errorcode;
        this.message = message;
        this.data = data;
    }

    public ResponseDataErrorcode(T data) {
        this();
        this.data = data;
    }

    /**
     * @return the code
     */
    public int getErrorcode() {
        return errorcode;
    }

    /**
     * @param errorcode the errorcode to set
     */
    public void setCode(int errorcode) {
        this.errorcode = errorcode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    public <T> T toClass(Class<T> clazz){
        if(null==data){
            return null;
        }
        JSONObject jsonResponse = (JSONObject)JSONObject.toJSON(data);
        T rslt = JSONObject.parseObject(jsonResponse.toJSONString(), clazz);
        return rslt;
    }

}
