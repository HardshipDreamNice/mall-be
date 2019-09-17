package com.torinosrc.service;

import com.alibaba.fastjson.JSON;
import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.commons.utils.HttpPostUtils;
import com.torinosrc.commons.utils.image.CompoundImageInfo;
import com.torinosrc.commons.utils.image.ImageUtils;
import com.torinosrc.commons.utils.makePicture;
import com.torinosrc.dao.accesstoken.AccessTokenDao;
import com.torinosrc.model.entity.accesstoken.AccessToken;
import com.torinosrc.service.accesstoken.AccessTokenService;
import com.torinosrc.service.weixin.impl.WechatServiceImpl;
import net.sf.json.JSONObject;
import org.json.XML;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/12/6.
 */



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class wximageuploadTest {

    private static final Logger LOG = LoggerFactory
            .getLogger(WechatServiceImpl.class);

    @Value("${weixin.appId}")
    private String APP_ID;

    @Value("${weixin.secret}")
    private String SECRET;

    @Value("${weixin.mchId}")
    private String MCH_ID;

    @Value("${weixin.key}")
    private String KEY;

    @Value("${weixin.qrCodePath}")
    private String QRCODE_PATH;

    @Value("${weixin.backgroundPic}")
    private String BACKGROUND_PIC;

    @Value("${weixin.backgroundGroupPic}")
    private String BACKGROUND_GROUP_PIC;

    @Value("${weixin.notifyUrl}")
    private String notifyUrlConfig;

    @Value("${weixin.body}")
    private String contentBody;

    @Value("${weixin.notifyRUrl}")
    private String notifyRefundUrlConfig;

    @Value("${weixin.templateId}")
    private String templateId;


    @Value("${weixin.shopQrCodeBackGroupPic}")
    private String SHOP_QRCODE_BACKGROUP_PIC;


    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private AccessTokenService accessTokenService;


    private final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    private final String GET_QRCODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

    //二维码跳转页面
    private final String QRCODE_PAGE = "pages/index/authorization/authorization";

    private final String ACCESS_TOKEN = "ACCESS_TOKEN";


    @Test
    public void wxjudgeimageupload() {

        String accessToken = accessTokenService.getAccessToken();
        String getQrCodeUrl = GET_QRCODE_URL + "?access_token=" + accessToken;

        JSONObject getQrCodeParams = new JSONObject();
        //携带在二维码中的参数，进行BASE64处理 shopId&productId&redirectUrlId&distribution&teamId
        String outputPath = QRCODE_PATH + "/shopoutimages/";
        String fileName = 3111 + ".jpg";
        String preShopQrCodePath = QRCODE_PATH + "/shopqrcode/";
        String shopQrCodePath = preShopQrCodePath + fileName;
        File file = new File(shopQrCodePath);
        // 返回给前端的相对路径
        String shopQrCodePathReturn = "";
        if (file.exists() && file.length() > 1000) {
            shopQrCodePathReturn = shopQrCodePath;
            System.out.println("不重新生成小程序二维码: " + shopQrCodePathReturn);
            LOG.info("不重新生成小程序二维码：" + shopQrCodePathReturn);
        } else {
            // 返回给前端的相对路径
            shopQrCodePathReturn = "E:\\www\\root\\mall_160\\public\\twoImage\\2.jpg";
            System.out.println("重新生成小程序二维码: " + shopQrCodePathReturn);
            LOG.info("重新生成小程序二维码：" + shopQrCodePathReturn);
        }
        String backgroundPic = QRCODE_PATH + "/shopbpic/" + SHOP_QRCODE_BACKGROUP_PIC;
        //图片信息
        List<CompoundImageInfo> compoundImageInfoList = Arrays.asList(new CompoundImageInfo(shopQrCodePathReturn, 138, 445, 144, 144));
        String resultImageFileName = ImageUtils.compoundPirtureAndFont(backgroundPic, outputPath, fileName, new ArrayList<>(), compoundImageInfoList);
        LOG.info("重新生成小程序二维码合成图");
    }



   @Test
    public void maddi() throws Exception {
//        String urlPath="E:\\www\\root\\mall_160\\public\\twoImage\\1.png";
        String urlPath="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3204965147,1604536632&fm=15&gp=0.jpg";
       String image="image";
       AccessToken accessTokenEntity = accessTokenDao.findByName("ACCESS_TOKEN");
       String accessToken = accessTokenEntity.getValue();
       System.out.println("token的码："+accessToken);
       this.getMediaIdFromUrl(urlPath,image);
    }

    /**
     * 公众号登录转跳到小程序二维码图片上传到微信服务器
     */
    public String getMediaIdFromUrl(String urlPath, String fileType) throws Exception {
        String result = null;
        String accessToken = accessTokenService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=" + fileType;
        String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1);
        // 获取网络图片
        URL mediaUrl = new URL(urlPath);
        HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
        meidaConn.setDoOutput(true);
        meidaConn.setRequestMethod("GET");

        /**
         * 第一部分
         */
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(meidaConn.getInputStream());
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        meidaConn.disconnect();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            InputStream inputStream = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
//            log.info("发送POST请求出现异常！ {}", e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        System.out.println("resoufasdff "+result);
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(jsonObject.getString("media_id"));
        return jsonObject.getString("media_id");
    }
}