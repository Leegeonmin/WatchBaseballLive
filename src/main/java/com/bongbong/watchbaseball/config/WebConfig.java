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

    /**
     * Creates and configures a RestClient bean.
     *
     * <p>This method sets up a RestClient with a custom URI builder factory that disables URI encoding,
     * and registers a request interceptor which logs the URI of each outgoing request before executing it.</p>
     *
     * @return a configured RestClient instance for making HTTP requests
     */
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
