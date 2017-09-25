package com.xiangyong.manager.interceptor;

import com.google.common.base.Strings;
import com.xiangyong.manager.biz.interfaces.TokenBizService;
import com.xiangyong.manager.core.util.WsConstants;
import com.xiangyong.manager.dto.SimpleMemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger= LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    private TokenBizService tokenBizService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        /*HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod ();
        Class<?> aClass = handlerMethod.getBeanType();*/

        // 从 header 中得到 token
        String token = request.getHeader (WsConstants.REQUEST_TOKEN);
        if(Strings.isNullOrEmpty(token)
            && !Strings.isNullOrEmpty(request.getQueryString())
            )
        {
            //从query string 里面取token
            Optional<String> queryStringToken = Arrays.stream(request.getQueryString().split("&")).filter(x -> x.startsWith(WsConstants.REQUEST_TOKEN + "=")).findFirst();
            String[] tokenParams = queryStringToken.isPresent() ? queryStringToken.get().split("="):null;
            if(tokenParams.length == 2) {
                token = tokenParams[1];
            }
        }

        // 验证 token
        SimpleMemberDto model = tokenBizService.GetSimpleMemberDtoByToken(token);
        if (model != null) {
            // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
            request.setAttribute ("userInfo", model);
            return true;
        }

        /*// 如果验证 token 失败，
            // 并且方法注明了 KucunAuthorization，返回 401 错误
            // 或者类型注明了 KucunAuthorization，返回 401 错误
        if (method.isAnnotationPresent (KucunAuthorization.class)
                || aClass.isAnnotationPresent (KucunAuthorization.class)) {*/
            logger.warn("登陆失效, token:{}", token);
            response.setStatus (HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().print(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return false;
        /*}
        return true;*/
    }
}
