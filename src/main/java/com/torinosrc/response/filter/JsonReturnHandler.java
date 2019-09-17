package com.torinosrc.response.filter;

import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.response.json.JSON;
import com.torinosrc.response.json.JSONS;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.torinosrc.response.filter.*;

/**
 * 自定义返回内容处理器
 * 如果接口注解包含@JSON则执行自定义的处理器，否则执行spring默认处理器
 */
public class JsonReturnHandler implements HandlerMethodReturnValueHandler, BeanPostProcessor {
    List<ResponseBodyAdvice<Object>> advices = new ArrayList<>();

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        boolean hasJSONAnno = returnType.getMethodAnnotation(JSON.class) != null || returnType.getMethodAnnotation(JSONS.class) != null;
        return hasJSONAnno;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        for (int i=0;i<advices.size();i++){
            ResponseBodyAdvice<Object> ad = advices.get(i);
            if (ad.supports(returnType, null)) {
                returnValue = ad.beforeBodyWrite(returnValue, returnType, MediaType.APPLICATION_JSON_UTF8, null,
                        new ServletServerHttpRequest(webRequest.getNativeRequest(HttpServletRequest.class)),
                        new ServletServerHttpResponse(webRequest.getNativeResponse(HttpServletResponse.class)));
            }
        }

        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        HttpServletRequest request=webRequest.getNativeRequest(HttpServletRequest.class);
        CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();

        Annotation[] annos = returnType.getMethodAnnotations();
        Arrays.asList(annos).forEach(a -> {
            if (a instanceof JSON) {
                JSON json = (JSON) a;
                if(StringUtils.isNotBlank(request.getParameter("field"))){
                    String filter = new String(Base64Utils.decodeFromString(request.getParameter("field")));
                    Field field = com.alibaba.fastjson.JSON.parseObject(filter, Field.class);
                    jsonSerializer.filter(json.type(),field.getInclude(),field.getFilter());
                }else{
                    jsonSerializer.filter(json);
                }

            } else if (a instanceof JSONS) {
                JSONS jsons = (JSONS) a;
                Arrays.asList(jsons.value()).forEach(json -> {
                    jsonSerializer.filter(json);
                });
            }
        });

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Object data=TorinoSrcCommonUtils.getFieldValueByName("body",returnValue);
        String json = jsonSerializer.toJson(data);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(json);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
//        response.getWriter().write(json);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ResponseBodyAdvice) {
            advices.add((ResponseBodyAdvice<Object>) bean);
        } else if (bean instanceof RequestMappingHandlerAdapter) {
            List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(
                    ((RequestMappingHandlerAdapter) bean).getReturnValueHandlers());
            JsonReturnHandler jsonHandler = null;
            for (int i = 0; i < handlers.size(); i++) {
                HandlerMethodReturnValueHandler handler = handlers.get(i);
                if (handler instanceof JsonReturnHandler) {
                    jsonHandler = (JsonReturnHandler) handler;
                    break;
                }
            }
            if (jsonHandler != null) {
                handlers.remove(jsonHandler);
                handlers.add(0, jsonHandler);
                ((RequestMappingHandlerAdapter) bean).setReturnValueHandlers(handlers); // change the jsonhandler sort
            }
        }
        return bean;
    }
}
