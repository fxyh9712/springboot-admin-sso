package com.fxyh.sso.util.aop;

import com.alibaba.fastjson.JSON;
import com.fxyh.sso.model.SsoUsers;
import com.fxyh.sso.model.SysLog;
import com.fxyh.sso.service.SysLogService;
import com.fxyh.sso.util.ControllerUtil;
import com.fxyh.sso.util.annotation.MyLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * @ClassName: SysLogAspect
 * @description: 系统日志切面
 * @author: fengzhaoquan
 * @create: 2019-05-21 08:21
 * @Version 1.0
 **/
@Aspect
@Component
public class SysLogAspect {
    private final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    @Autowired
    private SysLogService sysLogService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.fxyh.sso.util.annotation.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        logger.info("切面。。。。。");
        //保存日志
        SysLog sysLog = new SysLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperation(value);//保存获取的操作
            logger.info("操作：" + value);
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        logger.info("请求方法名：" + methodName);
        sysLog.setMethod(className + "." + methodName);
        //请求的参数
//        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params;
        try {
            params = JSON.toJSONString(ControllerUtil.getHttpServletRequest().getParameterMap());
        } catch (Exception e) {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            e.printStackTrace(new java.io.PrintWriter(buf, true));
            params = buf.toString();
            try {
                buf.close();
            } catch (IOException ex) {
                params = "发生异常，获取堆栈信息时，关流异常：" + e.getMessage();
            }
        }
        logger.info("参数：" + params);
        if ("登录".equals(myLog.value())) {
            sysLog.setParams("登录参数隐藏。。。");
        } else {
            sysLog.setParams(params);
        }
        sysLog.setCreateDate(new Date());
        logger.info("请求时间：" + new Date());
        //获取用户名
        String username;
        try {
            username = ((SsoUsers) (ControllerUtil.getHttpSession().getAttribute("DT_LOGIN_USER"))).getName();
        } catch (Exception e) {
            username = "大众用户";
        }
        sysLog.setUsername(username);
        logger.info("用户：" + username);
        String ip = ControllerUtil.getRemoteAddress();
        //获取用户ip地址
        sysLog.setIp(ip);
        logger.info("ip：" + ip);
        //调用service保存SysLog实体类到数据库
        sysLogService.insert(sysLog);
    }

}
