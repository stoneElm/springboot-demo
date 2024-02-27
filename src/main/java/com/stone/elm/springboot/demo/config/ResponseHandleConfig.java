package com.stone.elm.springboot.demo.config;

import com.stone.elm.springboot.demo.basictech.common.response.HandleResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ResponseHandleConfig implements WebMvcConfigurer {

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(converter());
//    }
//
//    @Bean
//    public HandleResponse converter() {
//        return new HandleResponse();
//    }
}
