/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.commons.exceptions;


/**
 * <b><code>TorinoSrcServiceException</code></b>
 * <p/>
 * TorinoSrcServiceException
 * <p/>
 * <b>Creation Time:</b> 2016/10/31 20:29.
 *
 * @author xiedongmei
 * @version $ {Revision} 2016/10/31
 * @since torinosrc-commons 0.0.1
 */
public class TorinoSrcServiceException extends TorinoSrcApplicationException {

    /**
     * The Constant CONFLICT.
     */
    public static final String CONFLICT = "Conflict";

    /**
     * The Constant NOT_FOUND.
     */
    public static final String NOT_FOUND = "NotFound";

    /**
     * Serialization ID
     * @since diamond-service-commons 1.0
     */
    private static final long serialVersionUID = 3894491214688661572L;

    /**
     * Instantiates a new diamond service exception.
     */
    public TorinoSrcServiceException(){}

    /**
     * Instantiates a new diamond service exception.
     *
     * @param message the message
     */
    public TorinoSrcServiceException(String message){
        super(message);
    }

    /**
     * Instantiates a new diamond service exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public TorinoSrcServiceException(String message, String errorCode){
        super(message);
        this.setErrorCode(errorCode);
    }

    /**
     * Instantiates a new diamond service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TorinoSrcServiceException(String message, Throwable cause){
        super(message,cause);
    }

    /**
     * Instantiates a new Diamond service exception.
     *
     * @param message   the message
     * @param cause     the cause
     * @param errorCode the error code
     */
    public TorinoSrcServiceException(String message, Throwable cause, String errorCode){
        super(message,cause);
        this.setErrorCode(errorCode);
    }

    /**
     * Instantiates a new diamond service exception.
     *
     * @param cause the cause
     */
    public TorinoSrcServiceException(Throwable cause){
        super(cause);
    }

    /**
     * Instantiates a new diamond service exception.
     *
     * @param cause     the cause
     * @param errorCode the error code
     */
    public TorinoSrcServiceException(Throwable cause, String errorCode){
        super(cause);
        this.setErrorCode(errorCode);
    }

}
