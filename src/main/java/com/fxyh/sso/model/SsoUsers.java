package com.fxyh.sso.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author fengzhaoquan
 * @since 2019-05-23
 */
@TableName("sso_users")
public class SsoUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * user表主键
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户的邮箱
     */
    private String mail;
    /**
     * 用户的主页
     */
    private String url;
    /**
     * 用户显示的名称
     */
    private String screenName;
    /**
     * 用户注册时间
     */
    private Date created;
    /**
     * 最后活动时间
     */
    private Date activated;
    /**
     * 上次登录最后活跃时间
     */
    private Date logged;
    /**
     * 用户组
     */
    private String group;
    /**
     * 用户登录验证码
     */
    private String authCode;
    /**
     * 逻辑删除（1：有效，0：无效）
     */
    private Integer delStatus;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getActivated() {
        return activated;
    }

    public void setActivated(Date activated) {
        this.activated = activated;
    }

    public Date getLogged() {
        return logged;
    }

    public void setLogged(Date logged) {
        this.logged = logged;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Override
    public String toString() {
        return "SsoUsers{" +
        ", uid=" + uid +
        ", name=" + name +
        ", password=" + password +
        ", mail=" + mail +
        ", url=" + url +
        ", screenName=" + screenName +
        ", created=" + created +
        ", activated=" + activated +
        ", logged=" + logged +
        ", group=" + group +
        ", authCode=" + authCode +
        ", delStatus=" + delStatus +
        "}";
    }
}
