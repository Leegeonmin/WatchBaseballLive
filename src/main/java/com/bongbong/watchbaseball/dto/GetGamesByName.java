package com.bongbong.watchbaseball.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class GetGamesByName {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor

    public static class Response{
        private String oppositionTeam;
        private LocalDate gameDate;
    }
}
