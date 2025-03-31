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
     * Retrieves medium precipitation data for the given region.
     *
     * <p>This method formats the current date as "yyyyMMdd" and appends "0600" to form a forecast 
     * timestamp. It constructs a query string using the API key, data type, region identifier (regId), 
     * and the timestamp, then performs a GET request to the medium precipitation endpoint. The JSON 
     * response is mapped to a {@code MediumPrecipitationResponse} object.
     *
     * @param regId the region identifier for which to fetch medium precipitation data
     * @return the medium precipitation response mapped from the API's JSON response
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
