package com.bongbong.watchbaseball.service;

import com.bongbong.watchbaseball.dto.WeatherFromApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//창원NC파크                       11H20000
//대구삼성라이온즈파크         11H10000
//고척스카이돔                    11B00000
//광주기아챔피언스필드         11F20000
//인천SSG랜더스필드             11B00000
//수원KT위즈파크                  11B00000
//부산사직야구장                     11H20000
//서울종합운동장야구장              11B00000
//대전한화생명이글스파크         11C20000
//
//
//11H20000 : NC, 롯데
//11H10000 : 대구
//11B00000 : 키움, SSG, KT, 잠실, 두산
//11F20000 : 기아
//11C20000 : 한화


// 날짜 저장 로직
// API에서 날짜를 받아옴
// 해당 날짜 기준 3~10일후의 날씨를 받아옴
// DB에 해당하는 데이터들 업데이트(없다면 추가, 있다면 수정)


@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    @Value("${spring.weather-open-api.key}")
    private String api_key;

    @Value("${spring.weather-open-api.base-url}")
    private String baseURL;

    private final ObjectMapper objectMapper;

    public WeatherFromApi fetchWeatherData() {
        WeatherFromApi weatherFromApi = null;
        try {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String url = baseURL + "?serviceKey=" + api_key +
                    "&numOfRows=3&pageNo=1&dataType=JSON&regId=11H20000&tmFc=" + "20240709" + "0600";
            String jsonResponse = sendGetRequest(url);
            weatherFromApi = parseJsonResponse(jsonResponse);
            log.info("Fetched weather forecast: {}", weatherFromApi);

        } catch (Exception e) {
            log.error("Failed to fetch weather data", e);
        }
        return weatherFromApi;
    }

    private String sendGetRequest(String urlString) throws Exception{
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }
    private WeatherFromApi parseJsonResponse(String jsonResponse) throws Exception{
        JsonNode root = objectMapper.readTree(jsonResponse);
        JsonNode item = root.path("response").path("body").path("items").path("item").get(0);
        return objectMapper.treeToValue(item, WeatherFromApi.class);
    }
//    public static void main(String[] args) {
//        String URL = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst" +
//                "?serviceKey=xQQ6NzCVdFkitDSD9Aehb5kEqYMv4nY9jVfdXSQbpDSNYbFkMPYPgODD7UyH%2Biv%2F2%2BIW9GoCvOOb9SjiG4tC1g%3D%3D&numOfRows=3&pageNo=1" +
//                "&dataType=JSON&regId=11H20000&tmFc=202407090600";
//        try {
//            java.net.URL url = new URL(URL);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            int responseCode = connection.getResponseCode();
//
//            BufferedReader br;
//            if (responseCode == 200) {
//                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            } else {
//                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//            }
//
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//            while ((inputLine = br.readLine()) != null) {
//                System.out.println(inputLine);
//                response.append(inputLine);
//            }
//            System.out.println(response.toString());
//        } catch (Exception e) {
//            System.out.println("failed to get response");
//        }
//    }


}
