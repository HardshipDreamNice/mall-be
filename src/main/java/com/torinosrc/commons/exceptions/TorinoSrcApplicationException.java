/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.commons.exceptions;

/**
 * <b><code>TorinoSrcApplicationException</code></b>
 * <p/>
 * TorinoSrcApplicationException
 * <p/>
 * <b>Creation Time:</b> 2016/10/31 20:29.
 *
 * @author xiedongmei
 * @version $ {Revision} 2016/10/31
 * @since torinosrc-commons 0.0.1
 */
public class TorinoSrcApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1960405186815260702L;
    /**
     * Serialization ID
     *
     * @since diamond-service-commons 1.0
     */

    private String errorCode;

    /**
     * Instantiates a new Diamond application exception.
     */
    public TorinoSrcApplicationException() {
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param message the message
     */
    public TorinoSrcApplicationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TorinoSrcApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param cause the cause
     */
    public TorinoSrcApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * Returns the errorCode
     *
     * @return the errorCode
     * @since diamond -service-commons project_version
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the errorCode
     *
     * @param errorCode the errorCode to set
     * @since diamond -service-commons project_version
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
