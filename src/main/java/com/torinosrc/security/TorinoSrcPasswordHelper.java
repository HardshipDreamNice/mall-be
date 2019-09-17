/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.security;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.util.StringUtils;

/**
 * <b><code>TorinoSrcPasswordHelper</code></b>
 * <p/>
 * PasswordHelper的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:33:50.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
public class TorinoSrcPasswordHelper {
    private final static String ALGORITHM_NAME = "md5";
    private final static int HASH_ITERATIONS = 1024;

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static String encode(String username, String password) {
        return new SimpleHash(ALGORITHM_NAME, password,
                username.getBytes(), HASH_ITERATIONS).toHex();
    }

    /**
     *
     * @param username
     * @param password
     * @param encodePassword
     * @return
     */
    public static boolean match(String username, String password, String encodePassword) {
        boolean match = false;
        if(!StringUtils.isEmpty(encodePassword) && encodePassword.equals(encode(username,password))){
            match = true;
        }else {
            // nothing to do
        }
        return match;
    }


    public static void main(String[] args) {
        System.out.println(TorinoSrcPasswordHelper.encode("lvxin","lvxin"));
        System.out.println(match("lvxin","lvxin", TorinoSrcPasswordHelper.encode("lvxin","lvxin")));
        System.out.println(TorinoSrcPasswordHelper.encode("lvxin","lvxin"));
    }
}
