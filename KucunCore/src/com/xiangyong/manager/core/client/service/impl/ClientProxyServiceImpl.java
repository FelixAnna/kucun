package com.xiangyong.manager.core.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiangyong.manager.core.client.annotation.ClientApi;
import com.xiangyong.manager.core.client.annotation.ClientParam;
import com.xiangyong.manager.core.client.enums.RequestSource;
import com.xiangyong.manager.core.client.exception.ClientApiException;
import com.xiangyong.manager.core.client.model.ClientApiModel;
import com.xiangyong.manager.core.client.model.ClientProxyRequest;
import com.xiangyong.manager.core.client.model.ClientProxyResponse;
import com.xiangyong.manager.core.client.service.ClientProxyService;
import com.xiangyong.manager.core.exception.ApplicationException;
import com.xiangyong.manager.core.exception.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

@Component
public class ClientProxyServiceImpl implements ClientProxyService, ApplicationContextAware, InitializingBean {

    private ApplicationContext context;

    Map<String, ClientApiModel> apiMap = new HashMap<String, ClientApiModel>();

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static Logger logger = LoggerFactory.getLogger(ClientProxyServiceImpl.class);

    private void validate(Object params, Class<?>[] groups) {
        Set<ConstraintViolation<Object>> constraintViolations = null;
        if (groups != null) {
            constraintViolations = validator.validate(params, groups);
        } else {
            constraintViolations = validator.validate(params);
        }
        if (constraintViolations != null) {
            for (ConstraintViolation<Object> validation : constraintViolations) {
                throw new ApplicationException(ResponseCode.INVALID_PARAM, validation.getMessage());
            }
        }
    }

    public ClientProxyResponse doProxy(ClientProxyRequest request) throws ClientApiException {
        ClientProxyResponse response = new ClientProxyResponse();
        ClientApiModel apiModel = apiMap.get(request.getCmd());
        Method method = apiModel.getMethod();
        Class<?>[] paramsType = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
        if (paramsType != null) {
            Object[] params = new Object[paramsType.length];
            for (int i = 0; i < paramsType.length; ++i) {
                String paramsName = null;
                Class<?>[] groups = null;
                for (Annotation annotation : annotations[i]) {
                    if (annotation instanceof ClientParam) {
                        paramsName = ((ClientParam) annotation).value();
                    }
                    if (annotation instanceof Validated) {
                        groups = ((Validated) annotation).value();
                    }
                }
                // 设置 requestSource
                if (paramsType[i].equals(RequestSource.class)) {
                    params[i] = request.getRequestSource();
                }
                // 设置 openid
                else if (String.class.equals(paramsType[i]) && StringUtils.equals(paramsName, "openid")) {
                    params[i] = request.getOpenid();
                }
                // 设置 memberId
                else if (isLong(paramsType[i]) && StringUtils.equals(paramsName, "memberId")) {
                    params[i] = request.getMemberId();
                }
                // 设置 memberName
                else if (String.class.equals(paramsType[i]) && StringUtils.equals(paramsName, "memberName")) {
                    params[i] = request.getMemberName();
                }
                // 接口参数
                else if (!paramsType[i].isPrimitive() && !isPrimitive(paramsType[i]) && paramsName == null) {
                    try {
                        params[i] = JSON.parseObject(request.getRequest(), paramsType[i]);
                    } catch (Exception e) {
                        throw new ClientApiException("参数不合法");
                    }
                    validate(params[i], groups); // 参数校验
                } else {
                    logger.warn("参数 [" + paramsName + "]，类型 [" + paramsType[i] + "] 不能识别");
                }
            }
            Object res = null;
            try {
                res = method.invoke(apiModel.getApiObject(), params);
                if(!(res instanceof  ResponseCode)){
                    res = ResponseCode.buildResponse(res);
                }
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof ApplicationException) {
                    ApplicationException exception = (ApplicationException) e.getTargetException();
                    throw exception;
                } else {
                    throw new ClientApiException("系统错误", e);
                }
            } catch (IllegalAccessException e) {
                throw new ClientApiException("系统错误", e);
            } catch (IllegalArgumentException e) {
                throw new ClientApiException("参数不匹配", e);
            } catch (Exception e) {
                throw new ClientApiException(e.getMessage(), e);
            }
            response.setResponse(JSON.toJSONString(res));
        } else {
            throw new ClientApiException("执行的方法参数不能为空");
        }
        return response;
    }

    private boolean isLong(Class<?> clz) {
        return long.class.equals(clz) || Long.class.equals(clz);
    }

    private boolean isPrimitive(Class<?> cls) {
        return cls.isPrimitive() || cls == String.class || cls == Boolean.class || cls == Character.class || Number.class.isAssignableFrom(cls) || Date.class
                .isAssignableFrom(cls) || List.class.isAssignableFrom(cls) || Map.class.isAssignableFrom(cls) || Set.class
                .isAssignableFrom(cls);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        Map<String, Object> apiServices = context.getBeansWithAnnotation(ClientApi.class);
        for (Entry<String, Object> entity : apiServices.entrySet()) {
            Object apiService = entity.getValue();
            String[] prefixUrls = apiService.getClass().getAnnotation(ClientApi.class).value();
            Method[] methods = apiService.getClass().getMethods();
            for (Method method : methods) {
                ClientApi clientApi = method.getAnnotation(ClientApi.class);
                if (clientApi == null) {
                    continue;
                }
                ClientApiModel clientApiModel = new ClientApiModel();
                clientApiModel.setMethod(method);
                clientApiModel.setApiObject(apiService);
                for (String url : clientApi.value()) {
                    for (String prefixUrl : prefixUrls) {
                        String finalUrl = prefixUrl + "/" + url;
                        if(!finalUrl.startsWith("/")){
                            finalUrl = "/" + finalUrl;
                        }
                        finalUrl = finalUrl.replaceAll("//","/");
                        if(finalUrl.endsWith("/")){
                            finalUrl.substring(0,finalUrl.length()-1);
                        }
                        apiMap.put(finalUrl, clientApiModel);
                    }
                }
            }
        }
    }

}
