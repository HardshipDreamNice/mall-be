package com.torinosrc.commons.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.torinosrc.commons.constants.SmsConstant;
import com.torinosrc.commons.exceptions.TorinoSrcApplicationException;

import java.text.SimpleDateFormat;

/**
 * 短信验证的 util
 */
public class SmsUtils {

    /**
     * 短信通知服务（meeting版本）
     * 阿里 API 文档：https://help.aliyun.com/document_detail/55284.html?spm=5176.doc55326.6.554.FrHJUY
     *
     * @param phoneNumber  手机号
     * @param freeSignName 签名
     * @param templateCode 模板编号
     * @param paramString  参数
     * @return 随机码（4位）
     */
    // TODO:具体的传参和逻辑要根据实际需求
    public static boolean smsNotify(String phoneNumber, String freeSignName, String templateCode, String paramString, Long date, String... name) {

        try {
            //设置超时时间-可自行调整
//            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //替换成你的AK
            final String accessKeyId = SmsConstant.SMS_APP_KEY;//你的accessKeyId,参考本文档步骤2
            final String accessKeySecret = SmsConstant.SMS_APP_SECRET;//你的accessKeySecret，参考本文档步骤2
            //初始化ascClient,暂时不支持多region
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                    accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(freeSignName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String res = simpleDateFormat.format(date * 1000);
            String[] split = res.split("-");
            request.setTemplateParam(String.format(paramString, split[0], split[1], split[2], name));


            //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            System.out.println("SMS SendSmsResponseMessage:" + sendSmsResponse.getMessage());
            if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
                //请求成功
                return true;
            }
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    //短信验证码
    // TODO:阿里云新版 具体的传参和逻辑要根据实际需求
    public static boolean smsVerificationCode(String phoneNumber, String freeSignName, String templateCode, String paramString, Integer smsCode) {
        try {
            //设置超时时间-可自行调整
//            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //TODO //替换成你的AK
            final String accessKeyId = SmsConstant.SMS_AK_ID;//你的accessKeyId,参考本文档步骤2
            final String accessKeySecret = SmsConstant.SMS_AK_SECRET;//你的accessKeySecret，参考本文档步骤2
            //初始化ascClient,暂时不支持多region
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                    accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(freeSignName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");

            request.setTemplateParam(String.format(paramString, smsCode));
            //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");

            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            System.out.println(sendSmsResponse.getMessage());
            if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
                //请求成功
                return true;
            }
            throw new TorinoSrcApplicationException(sendSmsResponse.getMessage());
        } catch (ClientException e) {
            throw new TorinoSrcApplicationException("短信验证码发送失败，请稍后再试");
//            e.printStackTrace();
//            return false;
        }
    }

}
