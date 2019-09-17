/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.commons.utils.message;

import com.torinosrc.model.view.message.TorinoSrcMessage;

/**
 * <b><code>MessageUtils</code></b>
 * <p/>
 * MessageUtils的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Oct 01 18:45:41 GMT+08:00 2017.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-common 1.0.0
 */
public class MessageUtils {

    /**
     * 设置请求返回参数
     *
     * @param messageCode 消息状态码
     * @param messageStatus 消息状态
     * @param messageDescription 消息描述
     * @param data 消息具体数据
     * @param <T> 消息具体数据类型
     * @return TorinoSrcMessage封装好的数据
     */
    public static <T> TorinoSrcMessage<T> setMessage(String messageCode, String messageStatus, String messageDescription, T data){
        TorinoSrcMessage<T> torinoSrcMessage = new TorinoSrcMessage<T>();
        torinoSrcMessage.setMessageCode(messageCode);
        torinoSrcMessage.setMessageStatus(messageStatus);
        torinoSrcMessage.setMessageDescription(messageDescription);
        torinoSrcMessage.setData(data);
        return torinoSrcMessage;
    }
}
