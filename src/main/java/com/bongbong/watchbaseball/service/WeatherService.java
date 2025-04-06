package com.bongbong.watchbaseball.service;

import com.bongbong.watchbaseball.domain.StadiumEntity;
import com.bongbong.watchbaseball.domain.WeatherEntity;
import com.bongbong.watchbaseball.dto.weatherapi.MediumPrecipitationResponse;
import com.bongbong.watchbaseball.dto.weatherapi.MediumTemperatureResponse;
import com.bongbong.watchbaseball.exception.CustomException;
import com.bongbong.watchbaseball.provider.WeatherProvider;
import com.bongbong.watchbaseball.repository.StadiumEntityRepository;
import com.bongbong.watchbaseball.repository.WeatherEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.bongbong.watchbaseball.exception.ErrorCode.WEATHER_DATE_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    private final StadiumEntityRepository stadiumEntityRepository;
    private final WeatherProvider weatherProvider;
    private final WeatherEntityRepository weatherEntityRepository;
    private final GameService gameService;

    //날씨 예보 저장 스케쥴링 메서드
    @Transactional
    public void saveWeatherFromApiScheduling() {
        List<StadiumEntity> stadiumEntityList = stadiumEntityRepository.findAll();

        for (StadiumEntity stadiumEntity : stadiumEntityList) {
            String mediumPrecipitationCode = stadiumEntity.getMediumPrecipitation();
            String mediumTemperatureCode = stadiumEntity.getMediumTemperature();

            MediumPrecipitationResponse mediumPrecipitation = weatherProvider.getMediumPrecipitation(mediumPrecipitationCode);
            MediumTemperatureResponse mediumTemperature = weatherProvider.getMediumTemperature(mediumTemperatureCode);

            for (int day = 4; day <= 10; day++) {
                LocalDate date = LocalDate.now().plusDays(day);
                WeatherEntity weather = weatherEntityRepository.findByStadiumAndDate(stadiumEntity, date)
                        .orElse(null);
                double minTemp = getTempValue(day, true, mediumTemperature.getResponse().getBody().getItems().getItem().get(0));
                double maxTemp = getTempValue(day, false, mediumTemperature.getResponse().getBody().getItems().getItem().get(0));
                int precipitation = getPrecipitationValue(day, mediumPrecipitation.getResponse().getBody().getItems().getItem().get(0));
                String description = getWeatherDescription(day, mediumPrecipitation.getResponse().getBody().getItems().getItem().get(0));


                if (weather != null) {
                    weather.update(minTemp, maxTemp, precipitation, description);
                } else {
                    WeatherEntity newWeather = WeatherEntity.builder()
                            .stadium(stadiumEntity)
                            .date(date)
                            .minTemperature(minTemp)
                            .maxTemperature(maxTemp)
                            .precipitationProbability(precipitation)
                            .description(description)
                            .build();

                    weatherEntityRepository.save(newWeather);

                    // !! 게임 엔티티에 업데이트하는 로직 추가해야함
                    gameService.updateWeatherForGame(stadiumEntity.getLocation(), date, newWeather);
                }
            }

        }
    }

    private String getWeatherDescription(int day, MediumPrecipitationResponse.MediumPrecipitationItem item) {
        return switch (day) {
            case 4 -> item.getWf4Pm();
            case 5 -> item.getWf5Pm();
            case 6 -> item.getWf6Pm();
            case 7 -> item.getWf7Pm();
            case 8 -> item.getWf8();
            case 9 -> item.getWf9();
            case 10 -> item.getWf10();
            default -> throw new CustomException(WEATHER_DATE_ERROR);
        };
    }

    private int getPrecipitationValue(int day, MediumPrecipitationResponse.MediumPrecipitationItem item) {
        return switch (day) {
            case 4 -> Math.max(item.getRnSt4Am(), item.getRnSt4Pm());
            case 5 -> Math.max(item.getRnSt5Am(), item.getRnSt5Pm());
            case 6 -> Math.max(item.getRnSt6Am(), item.getRnSt6Pm());
            case 7 -> Math.max(item.getRnSt7Am(), item.getRnSt7Pm());
            case 8 -> item.getRnSt8();
            case 9 -> item.getRnSt9();
            case 10 -> item.getRnSt10();
            default -> throw new CustomException(WEATHER_DATE_ERROR);
        };
    }

    private double getTempValue(int day, boolean isMin, MediumTemperatureResponse.Item item) {
        return switch (day) {
            case 4 -> isMin ? item.getTaMin4() : item.getTaMax4();
            case 5 -> isMin ? item.getTaMin5() : item.getTaMax5();
            case 6 -> isMin ? item.getTaMin6() : item.getTaMax6();
            case 7 -> isMin ? item.getTaMin7() : item.getTaMax7();
            case 8 -> isMin ? item.getTaMin8() : item.getTaMax8();
            case 9 -> isMin ? item.getTaMin9() : item.getTaMax9();
            case 10 -> isMin ? item.getTaMin10() : item.getTaMax10();
            default -> throw new CustomException(WEATHER_DATE_ERROR);
        };
    }


}
