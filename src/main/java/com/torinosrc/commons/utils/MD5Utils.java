package com.torinosrc.commons.utils;

import com.torinosrc.commons.exceptions.TorinoSrcApplicationException;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    /**
     * MD5 加密
     * @param text
     * @return
     * @throws Exception
     */
    public static String md5(String text){
        try {
            //确定计算方法
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String encodeStr = base64en.encode(messageDigest.digest(text.getBytes("utf-8")));

            return encodeStr;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new TorinoSrcApplicationException("MD5加密失败");
        }
    }

    /**
     * 需要密钥的md5加密
     * @param text
     * @param key
     * @return
     */
    public static String md5WithKey(String text, String key) {
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text + key);
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5 验证
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5){
        //根据传入的密钥进行验证
        String md5Text = md5WithKey(text, key);
        if(md5Text.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }

        return false;
    }

}
