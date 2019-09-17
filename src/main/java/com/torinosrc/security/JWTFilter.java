/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.security;

import com.alibaba.fastjson.JSON;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <b><code>JWTFilter</code></b>
 * <p/>
 * JWTFilter的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:33:50.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");

        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean access = true;
        if (isLoginAttempt(request, response)) {
            try {
                access = executeLogin(request, response);
            } catch (Exception e) {
                PrintWriter writer = null;
                try {
                    response.setContentType("application/json;charset=UTF-8");
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    int httpStatus = 401;

                    writer = httpServletResponse.getWriter();
                    TorinoSrcMessage<String> torinoSrcMessage = null;
                    // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
                    if(e instanceof IncorrectCredentialsException){
                        torinoSrcMessage = MessageUtils.setMessage(MessageCode.AUTHORITY_TOKEN_INVALID_FAILURE, MessageStatus.ERROR, MessageDescription.OPERATION_FAILURE, e.getMessage());
                    }else if(e instanceof ExpiredCredentialsException){
                        torinoSrcMessage = MessageUtils.setMessage(MessageCode.AUTHORITY_TOKEN_EXPIRED_FAILURE, MessageStatus.ERROR, MessageDescription.OPERATION_FAILURE, e.getMessage());
                    }else if(e instanceof UnknownAccountException){
                        torinoSrcMessage = MessageUtils.setMessage(MessageCode.AUTHORITY_ACCOUNT_NOT_EXIST_FAILURE, MessageStatus.ERROR, MessageDescription.OPERATION_FAILURE, e.getMessage());
                    }else if(e instanceof DisabledAccountException){
                        torinoSrcMessage = MessageUtils.setMessage(MessageCode.AUTHORITY_ACCOUNT_DISABLED_FAILURE, MessageStatus.ERROR, MessageDescription.OPERATION_FAILURE, e.getMessage());
                    }else {
                        torinoSrcMessage = MessageUtils.setMessage(MessageCode.AUTHORITY_FAILURE, MessageStatus.ERROR, MessageDescription.OPERATION_FAILURE, "系统异常！");
                    }
                    httpServletResponse.setStatus(httpStatus);
                    writer.write(JSON.toJSON(torinoSrcMessage).toString());
                    writer.flush();
                    writer.close();
                } catch (IOException e1) {
                    // TODO:
//                    e1.printStackTrace();
                    if(writer!=null){
                        writer.close();
                    }
                }
//                response401(request, response);
            }
        }
        return access;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/401");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}