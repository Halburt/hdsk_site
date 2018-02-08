package com.houdask.site.common.result;

import java.io.Serializable;

/**
 * 定义json返回
 */
public class JsonReult  implements Serializable {

    /**
     * 通用成功
     */
    public static final String DICT_COMMON_SUCCESS = "1";
    /**
     * 通用失败
     */
    public static final String DICT_COMMON_ERROR = "0";
    /**
     * 参数错误
     */
    public static final String DICT_PARAM_ERROR = "100";//
    /**
     * 系统错误错误
     */
    public static final String DICT_SYSTEM_ERROR = "101";//
    /**
     * 权限错误
     */
    public static final String AUTH_ERROR = "102";//
    public String code;
    public String message;
    public Object data;

    public JsonReult() {
    }
    public JsonReult( Object data,String code, String message ) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static JsonReult success(Object data){
        return new JsonReult(data , JsonReult.DICT_COMMON_SUCCESS,"ok");
    }

    public static JsonReult success( ){
        return new JsonReult(null , JsonReult.DICT_COMMON_SUCCESS,"ok");
    }

    public static JsonReult error( String code, String message){
        return new JsonReult(null , code,message);
    }
    public static JsonReult error( String code, String message,Object data){
        return new JsonReult(data , code,message);
    }
    /**
     * 系统错误
     * @return
     */
    public static JsonReult error(  ){
        return new JsonReult("系统异常" , JsonReult.DICT_COMMON_ERROR, "系统维护中...");
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}