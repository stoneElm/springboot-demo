package com.stone.elm.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    /**
     * 跨域
     * @return null
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource config = new UrlBasedCorsConfigurationSource();
        config.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(config);
    }

    private CorsConfiguration buildConfig() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许任何域名访问
        corsConfiguration.addAllowedOrigin("*");
        // 允许任何header访问
        corsConfiguration.addAllowedHeader("*");
        // 允许任何方法访问
        corsConfiguration.addAllowedMethod("*");

        return corsConfiguration;
    }
}
