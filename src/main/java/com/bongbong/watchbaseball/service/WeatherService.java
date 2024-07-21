package com.bongbong.watchbaseball.service;

import com.bongbong.watchbaseball.dto.WeatherFromApi;
import com.bongbong.watchbaseball.util.HttpRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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


@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

  @Value("${spring.weather-open-api.key}")
  private String api_key;

  @Value("${spring.weather-open-api.base-url}")
  private String baseURL;

  private final ObjectMapper objectMapper;

  // 날짜 저장 로직
// API에서 날짜를 받아옴
// 해당 날짜 기준 3~10일후의 날씨를 받아옴
// DB에 해당하는 데이터들 업데이트(없다면 추가, 있다면 수정)


  /**
   * 날씨 업데이트 로직
   * 매일 오전 6시 기상청 공공 API를 통해 경기지역 날씨 업데이트
   * @return
   */
  public WeatherFromApi fetchWeatherData() {
    WeatherFromApi weatherFromApi = null;
    try {
      String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      String url = baseURL + "?serviceKey=" + api_key
          + "&numOfRows=3&pageNo=1&dataType=JSON&regId=11H20000&tmFc=" + currentDate + "0600";
      String jsonResponse = HttpRequest.sendGetRequest(url);
      weatherFromApi = parseWeatherCrawlingResponse(jsonResponse);
      log.info("Fetched weather forecast: {}", weatherFromApi);

    } catch (Exception e) {
      log.error("Failed to fetch weather data", e);
    }
    return weatherFromApi;
  }

  private WeatherFromApi parseWeatherCrawlingResponse(String jsonResponse) throws Exception {
    JsonNode root = objectMapper.readTree(jsonResponse);
    JsonNode item = root.path("response").path("body").path("items").path("item").get(0);
    return objectMapper.treeToValue(item, WeatherFromApi.class);
  }

}
