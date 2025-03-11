package com.bongbong.watchbaseball.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GetGameListByTeamNameResponse {
    private String oppositionTeam;
    private LocalDate gameDate;
    private String location;
}
