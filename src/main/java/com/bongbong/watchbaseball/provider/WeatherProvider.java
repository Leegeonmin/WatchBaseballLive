package com.bongbong.watchbaseball.provider;

import com.bongbong.watchbaseball.dto.weatherapi.MediumPrecipitationResponse;
import com.bongbong.watchbaseball.dto.weatherapi.MediumTemperatureResponse;
import com.bongbong.watchbaseball.exception.CustomException;
import com.bongbong.watchbaseball.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class WeatherProvider {
    @Value("${spring.weather-open-api.key}")
    private String api_key;

    @Value("${spring.weather-open-api.base-url}")
    private String baseURL;

    @Value("${spring.weather-open-api.medium-precipitation}")
    private String mediumPrecipitationURL;

    @Value("${spring.weather-open-api.medium-temperature}")
    private String mediumTemperatureURL;

    private final RestClient restClient;

    public MediumPrecipitationResponse getMediumPrecipitation(String regId) {
        MediumPrecipitationResponse response = restClient.get()
                .uri(mediumPrecipitationURL + getUrlQuery(regId))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        (req, res) -> {
                            throw new CustomException(ErrorCode.OPENAPI_SERVER_ERROR);
                        }
                )
                .body(MediumPrecipitationResponse.class);
        return response;
    }

    public MediumTemperatureResponse getMediumTemperature(String regId) {
        MediumTemperatureResponse response = restClient.get()
                .uri(mediumTemperatureURL + getUrlQuery(regId))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        (req, res) -> {
                            throw new CustomException(ErrorCode.OPENAPI_SERVER_ERROR);
                        }
                )
                .body(MediumTemperatureResponse.class);
        return response;
    }


    private String getUrlQuery(String regId) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        UriComponentsBuilder  url = UriComponentsBuilder.newInstance()
                .queryParam("serviceKey", api_key)
                .queryParam("dataType", "JSON")
                .queryParam("regId", regId)
                .queryParam("tmFc", currentDate + "0600");
        return url.build().toUriString();
    }
}
