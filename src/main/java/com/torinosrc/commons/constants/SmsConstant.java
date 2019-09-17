package com.torinosrc.commons.constants;

/**
 * 短信验证码常量类
 * Created by Administrator on 2017/7/31.
 */
public final class SmsConstant {

    // TODO:不同项目可能有不同配置
    // 短信验证服务的 url
    public static final String SMS_SERVER_URL = "http://gw.api.taobao.com/router/rest";
    // App Key
    public static final String SMS_APP_KEY = "24564798";
    // 密文
    public static final String SMS_APP_SECRET = "20eee08a967ad3464673091d0b3bd5e1";
    // 扩展
    public static final String SMS_EXTEND = "";
    // 短信类型
    public static final String SMS_TYPE = "normal";

    /*----------------------- meeting 版本的配置 ------------------------*/
    //    // 短信验证服务的 url
    //    public static final String SMS_SERVER_URL = "http://gw.api.taobao.com/router/rest";
    //    // App Key
    //    public static final String SMS_APP_KEY = "LTAIZkzFnZb3g6vx";
    //    // 密文
    //    public static final String SMS_APP_SECRET = "Q66vyFipCWAhARuTrHO51LLIDA81Zj";
    //    // 扩展
    //    public static final String SMS_EXTEND = "";
    //    // 短信类型
    //    public static final String SMS_TYPE = "normal";


    /*----------------------- 注册验证 ------------------------*/
    // 签名
    public static final String SMS_REGISTER_FREE_SIGN_NAME = "注册验证";
    // 模板编号
    public static final String SMS_REGISTER_TEMPLATE_CODE = "SMS_2740230";
    // 参数
    public static final String SMS_REGISTER_PARAM_STRING = "{code:'%d',product:'酒商网'}";



    /*----------------------- 登录验证 -------------------------*/
    // 签名
    public static final String SMS_LOGIN_FREE_SIGN_NAME = "登录验证";

    // 模板编号
    public static final String SMS_LOGIN_TEMPLATE_CODE = "SMS_2740232";
    // 参数
    public static final String SMS_LOGIN_PARAM_STRING = "{code:'%d',product:'酒商网'}";


    /*---------------------- 修改密码验证 ------------------------*/
    // 签名
    public static final String SMS_PASSWORD_FREE_SIGN_NAME = "变更验证";

    // 模板编号
    public static final String SMS_PASSWORD_TEMPLATE_CODE = "SMS_2740228";
    // 参数
    public static final String SMS_PASSWORD_PARAM_STRING = "{code:'%d',product:'酒商网'}";


    /*----------------------- 账号绑定 ------------------------*/
    // 签名
    public static final String SMS_BINDING_FREE_SIGN_NAME = "变更验证";

    // 模板编号
    public static final String SMS_BINDING_TEMPLATE_CODE = "SMS_85660029";
    // 参数
    public static final String SMS_BINDING_PARAM_STRING = "{code:'%d',product:'酒商网'}";

    /*------------------------短信验证码-------------------------*/
    //Access Key ID
//    public static final String SMS_AK_ID = "LTAI5HapfjRu643E";
//
//    //Access Key Secret
//    public static final String SMS_AK_SECRET = "ogdLaJyWq8twFNnDo92HTIkX5gYXrt";
//
//    //签名
//    public static final String SMS_SIGN_NAME = "叮当家网";
//
//    //模板编号
//    public static final String SMS_TEMPLATE_CODE = "SMS_118625022____";
//
//    //参数
//    public static final String SMS_PARAM_CODE = "{code:'%d'}";


    public static final String SMS_AK_ID = "LTAIn6jq51v00TSg";

    //Access Key Secret
    public static final String SMS_AK_SECRET = "4uxYy3VtG1cIO5DVohdu1wWUk9ilWN";

    //签名
    public static final String SMS_SIGN_NAME = "都灵源链";

    //模板编号
    public static final String SMS_TEMPLATE_CODE = "SMS_141596594";

    //参数
    public static final String SMS_PARAM_CODE = "{code:'%d'}";

}
