package com.fxyh.sso.util.shiro;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fxyh.sso.enums.DeleteStatusEnum;
import com.fxyh.sso.model.SsoUsers;
import com.fxyh.sso.service.SsoUsersService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: MyShiroRealm
 * @description: 重写shiroRealm
 * @author: fengzhaoquan
 * @create: 2019-05-19 19:47
 * @Version 1.0
 **/
public class MyRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private SsoUsersService ssoUsersService;

    /**
     * @return
     * @Author fengzhaoquan
     * @Description 用于权限验证，博客系统只有一个admin，没有权限
     * @Date
     * @Param
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * @return
     * @Author fengzhaoquan
     * @Description 登录验证
     * @Date
     * @Param
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户账号
        String username = token.getPrincipal().toString();
        Wrapper wrapper = Condition.create()
                .eq("name", username)
                .eq("delStatus", DeleteStatusEnum.USEING.getValue());
        SsoUsers ssoUsers = ssoUsersService.selectOne(wrapper);
        if (ssoUsers == null) {
            throw new AuthenticationException("帐号不存在！");
        }
        logger.info("realm:" + ssoUsers.getPassword());
        //将查询到的用户账号和密码存放到 authenticationInfo用于后面的权限判断。第三个参数随便放一个就行了。
        return new SimpleAuthenticationInfo(ssoUsers.getName(), ssoUsers.getPassword(), "fxyh");
    }

    /**
     * @return
     * @Author fengzhaoquan
     * @Description 实际做密码校验逻辑
     * @Date
     * @Param
     **/
    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        //表单中的密码
        String tokenCredentials = new SimpleHash("MD5", String.valueOf((char[]) token.getCredentials()), "fxyh", 1024).toHex();
        //数据库中的密码
        String accountCredentials = (String) info.getCredentials();
        //认证失败，抛出密码密码不正确的异常
        if (!accountCredentials.equals(tokenCredentials)) {
            throw new IncorrectCredentialsException("密码错误！");
        }
    }

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        return principals.getPrimaryPrincipal() + ":authorization";
    }

    @Override
    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        return principals.getPrimaryPrincipal() + ":authentication";
    }

    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        return token.getPrincipal() + ":authentication";
    }
}
