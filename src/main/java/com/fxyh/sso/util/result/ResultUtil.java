package com.fxyh.sso.util.result;

/**
 * @ClassName: ResultUtil
 * @description: 返回结果工具类
 * @author: fengzhaoquan
 * @create: 2019-05-21 13:20
 * @Version 1.0
 **/
public class ResultUtil {
    /**
     * 请求成功返回
     * @param object
     * @return
     */
    public static Msg success(Object object){
        Msg msg=new Msg();
        msg.setCode(200);
        msg.setMsg("请求成功");
        msg.setData(object);
        return msg;
    }
    public static Msg success(){
        return success(null);
    }

    public static Msg error(Integer code,String resultmsg){
        Msg msg=new Msg();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        return msg;
    }
}