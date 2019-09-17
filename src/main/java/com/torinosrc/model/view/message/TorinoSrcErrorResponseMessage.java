/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.message;

import io.swagger.annotations.ApiModel;

/**
 * <b><code>TorinoSrcErrorResponseMessage</code></b>
 * <p/>
 * class_comment
 * <p/>
 * <b>Creation Time:</b> 2016/10/31 20:29.
 *
 * @author xiedongmei
 * @version $ {Revision} 2016/10/31
 * @since torinosrc-commons 0.0.1
 */
@ApiModel
public class TorinoSrcErrorResponseMessage {

    private String errorResponseMessage;

    public TorinoSrcErrorResponseMessage(){
        super();
    }

    public TorinoSrcErrorResponseMessage(String errorResponseMessage){
        super();
        this.errorResponseMessage = errorResponseMessage;
    }

    public String getErrorResponseMessage() {
        return errorResponseMessage;
    }

    public void setErrorResponseMessage(String errorResponseMessage) {
        this.errorResponseMessage = errorResponseMessage;
    }

}
