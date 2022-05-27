package com.dpp.deferred;

import com.dpp.deferred.controller.DeferredController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 记录每次请求信息
 *
 * @author db
 */
public class RequestLogInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(DeferredController.class);

    private String getBody(HttpServletRequest request) throws IOException {
        byte[] bytes = StreamUtils.copyToByteArray(request.getInputStream());
        return new String(bytes, request.getCharacterEncoding());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getDispatcherType() == DispatcherType.ASYNC){
            return true;
        }
        logger.info("请求客户端ip:{},请求路径:{},请求体{}", request.getRemoteAddr(),
                request.getRequestURL(), getBody(request));
        return true;
    }
}