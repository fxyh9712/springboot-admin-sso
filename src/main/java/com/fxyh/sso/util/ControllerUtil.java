package com.fxyh.sso.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: ControllerUtil
 * @description: ControllerUtil
 * @author: fengzhaoquan
 * @create: 2019-05-22 16:28
 * @Version 1.0
 **/
public class ControllerUtil {

    private ControllerUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 判断是否为Ajav请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        return ("XMLHttpRequest" .equalsIgnoreCase(request.getHeader("X-Requested-With")));
    }

    /**
     * post请求
     * @return
     */
    public static  boolean isPost() {
        HttpServletRequest request = getHttpServletRequest();
        String requersMethod = request.getMethod();
        return requersMethod.equals("POST") || "POST".equals(requersMethod);
    }

    /**
     * get请求
     *
     * @return
     */
    public static  boolean isGet() {
        HttpServletRequest request = getHttpServletRequest();
        String requersMethod = request.getMethod();
        return requersMethod.equals("GET") || "GET".equals(requersMethod);
    }

    /**
     * 获取请求域名，域名不包括http请求协议头
     *
     * @return 返回域名地址
     *
     */
    public static  String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        String path =  request.getContextPath();
        String domain = request.getServerName();
        if (request.getServerPort() == 80) {
            domain += path;
        } else {
            domain += ":" + request.getServerPort() + path;
        }
        return domain;
    }

    /**
     * 读取服务器主机ip信息
     *
     * @return 返回主机IP，异常将会获取不到ip
     */
    public static  String getHostIp() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            return addr.getHostAddress().toString();// 获得本机IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * 获取请求客户端ip
     *
     * @return ip地址
     *
     */
    public static  String getRemoteAddress() {
        HttpServletRequest request = getHttpServletRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static HttpServletRequest getHttpServletRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    public static HttpServletResponse getHttpServletResponse(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getResponse();
    }


    public static HttpSession getHttpSession(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getSession();
    }

    /**
     * 获取请求上传的文件集合
     * @return
     */
    public static List<MultipartFile> getMultipartFiles(){
        ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) getHttpServletRequest();
        List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
        if(!multipartResolver.isMultipart(shiroRequest)){
            return multipartFiles;
        }
        // 转换成多部分request
        Iterator<String> iterator = multipartRequest.getFileNames();
        while (iterator.hasNext()) {
            String name = iterator.next();
            List<MultipartFile> files = multipartRequest.getFiles(name);
            for(MultipartFile file : files){
                String fileName = file.getOriginalFilename();
                if (StringUtils.isEmpty(fileName)) {
                    continue;
                }
                multipartFiles.add(file);
            }
        }
        return multipartFiles;
    }

}