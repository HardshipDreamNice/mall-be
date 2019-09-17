package com.torinosrc.commons.utils;

import com.alibaba.fastjson.JSON;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/20.
 */
public class HttpPostUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpPostUtils.class);

    public static String doPost(String url, Map<String, String> params, String charset) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        // 设置Http Post数据
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        if (params != null) {
            Set<String> keySet = params.keySet();
            NameValuePair[] param = new NameValuePair[keySet.size()];
            int i = 0;
            for (String key : keySet) {
                param[i] = new NameValuePair(key, params.get(key));
                i++;
            }
            method.setRequestBody(param);
        }
        InputStream responseBodyStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                responseBodyStream = method.getResponseBodyAsStream();
                streamReader = new InputStreamReader(responseBodyStream, charset);
                reader = new BufferedReader(streamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (IOException e) {
            LOG.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            try {
                responseBodyStream.close();
                streamReader.close();
                reader.close();
            } catch (IOException e) {
                LOG.error("执行HTTP Post请求" + url + "时，发生异常，关闭流异常！", e);
                e.printStackTrace();
            }
            method.releaseConnection();
        }
        return response.toString();
    }


    /**
     * 获取小程序二维码
     *
     * @param jsonParam
     * @param urls
     * @param fileName  二维码路径及文件名
     * @return 0是失败 1是成功
     */
    public static String getQrCode(JSONObject jsonParam, String urls, String prePath, String fileName) {
        String qrCodePath = "";
        try {
            // 创建url资源
            URL url = new URL(urls);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            // 设置允许输入
            conn.setDoInput(true);
            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            // 转换为字节数组
            byte[] data = (jsonParam.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("contentType", "application/json");
            // 开始连接请求
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 写入请求的字符串
            out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();


            // 请求返回的状态
            if (HttpURLConnection.HTTP_OK != conn.getResponseCode()) {
                // TODO: exception need to be handled
                return qrCodePath;
            }

            // 请求返回的数据
            InputStream in1 = conn.getInputStream();
            File file = new File(prePath + fileName);
            if (!file.getParentFile().exists() && !file.isDirectory()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (BufferedInputStream bis = new BufferedInputStream(in1);
                 OutputStream os = new FileOutputStream(file);) {
                int len;
                byte[] arr = new byte[1024];
                byte[] arrTemp = new byte[0];
                while ((len = bis.read(arr)) != -1) {
                    int t = arrTemp.length;
                    arrTemp = new byte[t + len];
                    System.arraycopy(arr, 0, arrTemp, t, len);
                    os.write(arr, 0, len);
                    os.flush();
                }

                String text = new String(arrTemp);
                System.out.println("text: " + text);

                qrCodePath = prePath + fileName;
            } catch (Exception e1) {
                LOG.error("===========将数据流写成小程序二维码图片时出现异常============\n", e1);
                throw new TorinoSrcServiceException("将数据流写成小程序二维码图片时出现异常");
            }
        } catch (Exception e) {
            LOG.error("===========向微信请求小程序二维码时出现异常============\n", e);
            throw new TorinoSrcServiceException("向微信请求小程序二维码时出现异常");
        }
        return qrCodePath;
    }


    public static JSONObject weChatRefundPost(String xmlParams, String mchId, String certPath) {
        try {
            //配置证书
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            InputStream instream = HttpPostUtils.class.getResourceAsStream(certPath);
            InputStream instream = new FileInputStream((new File(certPath)));
            try {
                keyStore.load(instream, mchId.toCharArray()); //写密码
            } catch (Exception e) {
                LOG.error("加载证书异常", e);
                throw new TorinoSrcServiceException("加载证书异常");
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mchId.toCharArray()) //写密码
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";

            try {
                HttpPost httpPost = new HttpPost(url);
                StringEntity stringEntity = new StringEntity(xmlParams);
                httpPost.setEntity(stringEntity);
                System.out.println("executing request" + httpPost.getRequestLine());
                CloseableHttpResponse response = httpclient.execute(httpPost);
                try {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        System.out.println("Response content length: " + entity.getContentLength());
                        SAXReader saxReader = new SAXReader();
                        Document document = saxReader.read(entity.getContent());
                        Element rootElt = document.getRootElement();
                        System.out.println("return_code == " + rootElt.elementText("return_code"));
                        System.out.println("return_msg == " + rootElt.elementText("return_msg"));
                        String returnCode = rootElt.elementText("return_code");
                        JSONObject result = new JSONObject();
                        Document documentXml = DocumentHelper.parseText(xmlParams);
                        Element rootEltXml = documentXml.getRootElement();
                        if ("SUCCESS".equals(returnCode)) {
                            LOG.info("退款成功啦~");
                            result.put("transactionId", rootElt.elementText("transaction_id"));
                            result.put("outRefundNo", rootElt.elementText("out_refund_no"));
                            result.put("refund_id", rootElt.elementText("refund_id"));
                            result.put("refundFee", rootElt.elementText("refund_fee"));
                            result.put("status", "success");
                            result.put("msg", "success");
                        } else {
                            LOG.info("垃圾，居然退款失败！");
                            result.put("status", "false");
                            result.put("msg", rootElt.elementText("return_msg"));
                        }
                        return result;
                    }
                    EntityUtils.consume(entity);
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("status", "error");
            result.put("msg", e.getMessage());
            return result;
        }
    }

    public static String sendHttpPost(String url, Object object) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            HttpPost httpPost = new HttpPost(url);
            StringEntity s = new StringEntity(JSON.toJSONString(object), Charset.forName("UTF-8"));
            s.setContentEncoding("UTF-8");
            // 发送json数据需要设置contentType
            // s.setContentType("application/json");
            httpPost.setEntity(s);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            System.out.println("json" + responseContent);
        } catch (IOException e) {
            throw new TorinoSrcServiceException("请求http出错:" + url + "\n" + e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                throw new TorinoSrcServiceException("关闭连接，释放资源时出错：" + url, e);
            }
        }
        return responseContent;
    }

}
