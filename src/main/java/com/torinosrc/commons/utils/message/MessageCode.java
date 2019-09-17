/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.commons.utils.message;

/**
 * <b><code>MessageCode</code></b>
 * <p/>
 * MessageCode的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Oct 01 18:45:41 GMT+08:00 2017.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-common 1.0.0
 */
public class MessageCode {

    /**
     * 操作成功
     */
    public static final String SUCCESS = "1000";

    /**
     * 用户登陆成功
     */
    public static final String LOGIN_SUCCESS = "2000";

    /**
     * 用户登陆失败
     */
    public static final String LOGIN_FAILURE = "2009";

    /**
     * 用户名已存在
     */
    public static final String LOGIN_USERNAME_EXIST = "2100";

    /**
     * 用户名不存在
     */
    public static final String LOGIN_USERNAME_NOT_EXIST = "2101";

    /**
     * 验证码错误
     */
    public static final String LOGIN_VALIDATE_CODE_WRONG = "2200";

    /**
     * 授权
     */
    public static final String AUTHORITY_FAILURE = "4000";

    /**
     * TOKEN不正确
     */
    public static final String AUTHORITY_TOKEN_INVALID_FAILURE = "4010";

    /**
     * TOEKN过期
     */
    public static final String AUTHORITY_TOKEN_EXPIRED_FAILURE = "4011";

    /**
     * 账号不存在
     */
    public static final String AUTHORITY_ACCOUNT_NOT_EXIST_FAILURE = "4020";

    /**
     * 账号禁用
     */
    public static final String AUTHORITY_ACCOUNT_DISABLED_FAILURE = "4021";

    /**
     * 操作失败
     */
    public static final String FAILURE = "9000";

    /**
     * 库存不足
     */
    public static final String INSUFFICIENT_INVENTORY = "9001";

    /*
     * 手机号码已被注册
     */
    public static final String PHONE_NUMBER_HAS_BEEN_REGISTERED="5000";

    /*
     * 订单状态已被改变，请刷新
     */
    public static final String REFRESH_ORDER_STATUS="5010";

    /**
     * 类别已被关联
     */
    public static final String CONTENTTYPE_HAS_BEEN_ASSOCIATED= "5000";



}
