package com.torinosrc.commons.utils.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.HttpPostUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * <b><code>WeChatUtils</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>18:26
 *
 * @author PanXin
 * @version 1.0.0
 * @since bmw-be  1.0.0
 */
@Component
public class WeChatUtils {

    private static final Logger LOG = LoggerFactory
            .getLogger(WeChatUtils.class);

    /**
     * 微信获取用户openid
     * @param code
     * @param appId
     * @param secret
     * @return
     */
    public static String getOpenId(String code, String appId, String secret) {
        try {
            String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret
                    + "&js_code=" + code + "&grant_type=authorization_code";
            String result = new HttpRequestor().doGet(requestUrl);
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(result, Map.class);
            String openId = (String) map.get("openid");
            return openId;
        } catch (Exception e) {
            LOG.error("获取用户 openId 失败：\n" + e.getMessage());
            throw new TorinoSrcServiceException("获取用户 openId 失败");
        }
    }

    /**
     * 微信支付签名算法sign
     * @param parameters
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String createSign(SortedMap<Object,Object> parameters, String key){
        StringBuffer sb = new StringBuffer();
        //所有参与传参的参数按照 accsii 排序（升序）
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        String sign = DigestUtils.md5Hex(getContentBytes(sb.toString(), "utf-8")).toUpperCase();

        return sign;
    }

    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 小程序推送模板消息
     * @param accessToken
     * @param params
     * @return
     */
    public static String sendMiniProgramTemplate(String accessToken, JSONObject params) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;
        String result = HttpPostUtils.sendHttpPost(requestUrl, params);
        LOG.info("小程序模板消息的推送结果：" + result);
        return result;
    }

    /**
     * 生成小程序推送模板消息所需传递的 json 数据
     * @param miniProgramTemplateData
     * @return
     */
    public static JSONObject setMiniProgramTemplateData(MiniProgramTemplateData miniProgramTemplateData) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("touser", miniProgramTemplateData.getTouser());
        resultJson.put("template_id", miniProgramTemplateData.getTemplateId());
        resultJson.put("page", miniProgramTemplateData.getPage());
        resultJson.put("form_id", miniProgramTemplateData.getFormId());

        if (!StringUtils.isEmpty(miniProgramTemplateData.getEmphasisKeyword())) {
            resultJson.put("emphasis_keyword", miniProgramTemplateData.getEmphasisKeyword());
        }

        List<String> keywordValues = miniProgramTemplateData.getData();
        JSONObject dataJson = new JSONObject();
        for (int i = 0; i < keywordValues.size(); i++) {
            JSONObject keywordJson = new JSONObject();
            keywordJson.put("value", keywordValues.get(i));
            int index = i + 1;
            String key = "keyword" + index;
            dataJson.put(key, keywordJson);
        }

        resultJson.put("data", dataJson);

        return resultJson;
    }

}
