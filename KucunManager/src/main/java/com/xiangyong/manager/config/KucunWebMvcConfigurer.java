package com.xiangyong.manager.config;

import com.xiangyong.manager.interceptor.AuthorizationInterceptor;
import com.xiangyong.manager.interceptor.RequestLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class KucunWebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Bean
    protected RequestLogInterceptor getRequestLogInterceptorAdapter(){
        return new RequestLogInterceptor();
    }

    @Bean
    protected AuthorizationInterceptor getAuthorizationInterceptor(){
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRequestLogInterceptorAdapter())
                .addPathPatterns("/**");
        registry.addInterceptor(getAuthorizationInterceptor())
                .excludePathPatterns(
                        "/anonymous/**",
                        "/swagger**/**",
                        /*"/user/upload",*/
                        /*"/swagger-ui.html#/**",
                        "/swagger-resources/**",*/
                        "/error");
        super.addInterceptors(registry);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    /**
     * 跨域激活
     * @return
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
}
