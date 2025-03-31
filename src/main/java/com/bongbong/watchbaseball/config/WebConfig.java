package com.bongbong.watchbaseball.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Slf4j
@Configuration
public class WebConfig {
    @Value("${spring.weather-open-api.base-url}")
    private String baseURL;

    @Bean
    public RestClient restClient() {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        RestClient restClient = RestClient.builder()
                .uriBuilderFactory(uriBuilderFactory)
                .requestInterceptor((a,b,c)->{
                    log.info("request interceptor " + a.getURI());
                    var response = c.execute(a, b);
                    return response;
                })
                .build();
        return restClient;
    }
}
