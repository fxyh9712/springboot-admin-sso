package com.fxyh.sso.util.result;

/**
 * @ClassName: Msg
 * @description: 返回结果
 * @author: fengzhaoquan
 * @create: 2019-05-21 13:20
 * @Version 1.0
 **/
public class Msg<T> {

    /*错误码*/
    private Integer code;

    /*提示信息 */
    private String msg;

    /*具体内容*/
    private  T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
}