/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.commons.utils;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * <b><code>TorinoSrcServiceExceptionUtils</code></b>
 * <p/>
 * class_comment
 * <p/>
 * <b>Creation Time:</b> 2016/10/31 20:27.
 *
 * @author xiedongmei
 * @version $ {Revision} 2016/10/31
 * @since torinosrc-be project_version
 */
public class TorinoSrcServiceExceptionUtils {
    /**
     * Return http status.
     *
     * @param <T> the generic type
     * @param e   the e
     * @return the response entity
     */
    public static <T> ResponseEntity<T> getHttpStatus(Throwable e, Class<T> requiredType) {
        if (e instanceof TorinoSrcServiceException) {
            TorinoSrcServiceException dse = (TorinoSrcServiceException) e;

            if (TorinoSrcServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
            } else if (TorinoSrcServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<T>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Return http status.
     *
     * @param <T> the generic type returned as the type of the list
     * @param e   the e
     * @return the response entity
     */
    public static <T> ResponseEntity<List<T>> getHttpStatusWithList(Throwable e, Class<T> requiredType) {
        if (e instanceof TorinoSrcServiceException) {
            TorinoSrcServiceException dse = (TorinoSrcServiceException) e;

            if (TorinoSrcServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<List<T>>(HttpStatus.NOT_FOUND);
            } else if (TorinoSrcServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<List<T>>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<List<T>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Return http status.
     *
     * @param <T> the generic type returned as the type of the list
     * @param e the e
     * @return the response entity
     */
    public static <K, T> ResponseEntity<Map<K, List<T>>> getHttpStatusWithMap(
            Throwable e, Class<K> requiredType1, Class<T> requiredType2) {
        if (e instanceof TorinoSrcServiceException) {
            TorinoSrcServiceException dse = (TorinoSrcServiceException) e;

            if (TorinoSrcServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<Map<K, List<T>>>(
                        HttpStatus.NOT_FOUND);
            } else if (TorinoSrcServiceException.CONFLICT
                    .equals(dse.getErrorCode())) {
                return new ResponseEntity<Map<K, List<T>>>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<Map<K, List<T>>>(
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Write error log and return http status.
     *
     * @param errorMessage the error message
     * @param e            the e
     * @return the response entity
     */
    public static ResponseEntity<TorinoSrcErrorResponseMessage> getHttpStatusWithResponseMessage(String errorMessage, Throwable e) {
        if (e instanceof TorinoSrcServiceException) {
            TorinoSrcServiceException dse = (TorinoSrcServiceException) e;
            if (TorinoSrcServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<>(new TorinoSrcErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.NOT_FOUND);
            } else if (TorinoSrcServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<>(new TorinoSrcErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(new TorinoSrcErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(new TorinoSrcErrorResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gets the http status with response message.
     *
     * @param errorMessage the error message
     * @return the http status with response message
     */
    public static ResponseEntity<TorinoSrcErrorResponseMessage> getHttpStatusWithResponseMessage(
            String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<TorinoSrcErrorResponseMessage>(
                new TorinoSrcErrorResponseMessage(errorMessage), httpStatus);
    }

    /**
     * Gets the http status with response message.
     *
     * @param errorMessage the error message
     * @return the http status with response message
     */
    public static ResponseEntity<TorinoSrcMessage<TorinoSrcErrorResponseMessage>> getHttpStatusWithResponseTorinoSrcMessage(
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> errorMessage, Throwable e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof TorinoSrcServiceException) {
            TorinoSrcServiceException dse = (TorinoSrcServiceException) e;
            if (TorinoSrcServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                status = HttpStatus.NOT_FOUND;
            } else if (TorinoSrcServiceException.CONFLICT.equals(dse.getErrorCode())) {
                status = HttpStatus.CONFLICT;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        return new ResponseEntity<TorinoSrcMessage<TorinoSrcErrorResponseMessage>>(
                errorMessage, status);
    }
}
