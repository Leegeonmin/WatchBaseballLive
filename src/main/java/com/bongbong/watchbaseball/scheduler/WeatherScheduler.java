package com.bongbong.watchbaseball.scheduler;

import com.bongbong.watchbaseball.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherScheduler {
    private final WeatherService weatherService;

    @Scheduled(cron = "0 30 7 * * *")
    public void updateWeatherEveryMorning() {
        weatherService.saveWeatherFromApiScheduling();
    }
}
