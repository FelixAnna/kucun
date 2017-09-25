package com.xiangyong.manager.interceptor;

import com.google.common.base.Strings;
import com.xiangyong.manager.core.util.WsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Request: {} {} {} {} from: {}"
                ,request.getMethod()
                , request.getRequestURL()
                , Strings.nullToEmpty(request.getQueryString())
                , Strings.nullToEmpty(request.getHeader(WsConstants.REQUEST_TOKEN))
                , request.getRemoteAddr());
        return super.preHandle(request, response, handler);
    }
}
