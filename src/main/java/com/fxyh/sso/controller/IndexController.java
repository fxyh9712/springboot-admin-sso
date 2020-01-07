package com.fxyh.sso.controller;

import com.baomidou.mybatisplus.mapper.Condition;
import com.fxyh.sso.model.SsoUsers;
import com.fxyh.sso.service.SsoUsersService;
import com.fxyh.sso.util.annotation.MyLog;
import com.fxyh.sso.util.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: IndexController
 * @description: 首页控制器
 * @author: fengzhaoquan
 * @create: 2019-05-17 13:21
 * @Version 1.0
 **/
@Api("首页")
@Controller
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private SsoUsersService ssoUsersService;

    @ApiOperation(value = "首页", notes = "跳转首页")
    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @MyLog("首页")
    public Object index() {
        return "index";
    }

    @ApiOperation(value = "user", notes = "查询user信息")
    @RequestMapping(value = "/user", method = {RequestMethod.POST})
    @ResponseBody
    @MyLog("查询user信息")
    public Object userInfo() {
        return ssoUsersService.selectById(1);
    }

    @ApiOperation(value = "login", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "用户名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true)
    })
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    @MyLog("登录")
    public Object login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        // 创建Subject实例
        Subject subject = SecurityUtils.getSubject();
        // 将用户名及密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            Condition empty = Condition.create();
            empty.eq("name", username);
            SsoUsers ssoUsers = ssoUsersService.selectOne(empty);
            ssoUsers.setPassword(null);
            subject.getSession().setAttribute("DT_LOGIN_USER", ssoUsers);
            map.put("user", ssoUsers);
            map.put("token", subject.getSession().getId());
            return map;
        } catch (AuthenticationException e) {
            map.put("result", "fail");
            map.put("msg", e.getMessage());
            return map;
        }
    }

    /**
     * 登出  这个方法没用到,用的是shiro默认的logout
     *
     * @return
     */
    @ApiOperation(value = "logout", notes = "登出")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    @ResponseBody
    @MyLog("登出")
    public Object logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return ResultUtil.success();
        } catch (Exception e) {
            return ResultUtil.error(400, "登出失败！");
        }
    }

    @ApiOperation(value = "notLogin", notes = "没有登录访问需要登录的页面")
    @RequestMapping(value = "/notLogin", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @MyLog("没有登录访问需要登录的页面")
    public Object notLogin() {
        return ResultUtil.error(401, "没有登录");
    }

    @ApiOperation(value = "admin", notes = "admin")
    @RequestMapping(value = "/admin/index", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @MyLog("admin页面")
    public String admin() {
        return "admin";
    }

}
