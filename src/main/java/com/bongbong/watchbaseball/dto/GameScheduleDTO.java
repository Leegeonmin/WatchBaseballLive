package com.bongbong.watchbaseball.dto;

import java.time.LocalDateTime;

public record GameScheduleDTO(
        LocalDateTime gameTime,   // 게임 시간
        String location,          // 경기 위치
        String opponent,          // 상대 팀
        boolean isHomeGame,       // 홈 경기 여부
        String stadiumName,        // 구장 이름
        Double minTemperature,
        Double MaxTemperature,
        String weatherDescription,
        int rainProbability
) { }
