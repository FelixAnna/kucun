package com.xiangyong.manager.core.mvc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xiangyong.manager.core.dto.BaseDto;
import com.xiangyong.manager.core.exception.ResponseCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;

@Configuration
@ConditionalOnClass({FastJsonHttpMessageConverter.class})
@ConditionalOnProperty(
        name = {"spring.http.converters.preferred-json-mapper"},
        havingValue = "fastjson",
        matchIfMissing = true
)
public class FastJson2HttpMessageConverterConfiguration {

    @Bean
    @ConditionalOnMissingBean({FastJsonHttpMessageConverter.class})
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter() {
            @Override
            protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                //针对swaggerui特殊处理
//                if (obj instanceof Json || obj instanceof UiConfiguration) {
//                    super.writeInternal(obj, outputMessage);
//                    return;
//                } else if (obj instanceof List) {
//                    List list = ((List) obj);
//                    if(list.size() > 0 && list.get(0).getClass() == SwaggerResource.class){
//                        super.writeInternal(obj, outputMessage);
//                        return;
//                    }
//                }
                if (obj == null) {
                    super.writeInternal(ResponseCode.buildResponse(null), outputMessage);
                } else if (obj instanceof ResponseCode) {
                    super.writeInternal(obj, outputMessage);
                } else if (obj instanceof String) {
                    // 字符串的不再做一次封装
                    String text = (String) obj;
                    OutputStream out = outputMessage.getBody();
                    byte[] bytes = text.getBytes(this.getFastJsonConfig().getCharset());
                    out.write(bytes);
                } else if(obj instanceof BaseDto){
                    //不包装了让controller层自己包装
                    super.writeInternal(ResponseCode.buildResponse(obj), outputMessage);
                }else{
                    super.writeInternal(obj, outputMessage);
                }
            }
        };

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteMapNullValue
        );
        //fastjson1.2.15+ 已修复该问题，不需要做此配置
//        fastJsonConfig.getSerializeConfig().put(Json.class, SwaggerJsonSerializer.instance);
//        ValueFilter valueFilter = new ValueFilter() {
//            @Override
//            public Object process(Object object, String name, Object value) {
//                if (null == value) {
//                    value = "";
//                }
//                return value;
//            }
//        };
//        fastJsonConfig.setSerializeFilters(valueFilter);
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }
}