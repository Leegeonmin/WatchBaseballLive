package com.bongbong.watchbaseball.provider;

import com.bongbong.watchbaseball.dto.weatherapi.MediumPrecipitationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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

    /**
     * Retrieves medium precipitation data for a specified region.
     *
     * <p>This method constructs a query string using the current date (formatted as "yyyyMMdd") appended with a fixed
     * time component ("0600"), alongside the region identifier, API key, and data type parameters. It then sends a GET
     * request to the configured medium precipitation endpoint and maps the response to a {@code MediumPrecipitationResponse}.
     * </p>
     *
     * @param regId the region identifier for which precipitation data is requested
     * @return the medium precipitation response containing forecast data
     */
    public MediumPrecipitationResponse getMediumPrecipitation(String regId) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String urlQuery = "?" + "serviceKey=" + api_key + "&" +
                "dataType=JSON&" +
                "regId=" + regId + "&" +
                "tmFc=" + currentDate + "0600";
        MediumPrecipitationResponse response = restClient.get()
                .uri(mediumPrecipitationURL + urlQuery)
                .retrieve()
//                .onStatus() 에러처리 !!해야됨
                .body(MediumPrecipitationResponse.class);


        return response;
    }
}
