/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * <b><code>JWTUtil</code></b>
 * <p/>
 * JWTUtil的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:33:50.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
public class JWTUtil {


    // TODO: 修改
    /**
     * 过期时间4小时
     */
    private static final long EXPIRE_TIME = 4*60*60*1000;

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static boolean isExpiresToken(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            if(System.currentTimeMillis() < jwt.getExpiresAt().getTime() + EXPIRE_TIME){
                return false;
            }else {
                return true;
            }
        } catch (Exception exception) {
            return true;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            throw new TorinoSrcServiceException(e);
        }
    }

    /**
     * 获得token中的key的信息
     * @param token
     * @param key
     * @return
     */
    public static String getValue(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException e) {
            throw new TorinoSrcServiceException(e);
        }
    }

    /**
     * 生成签名,EXPIRE_TIME后过期
     * @param username 用户名
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String id, String username, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("id", id)
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new TorinoSrcServiceException(e);
        }
    }
}