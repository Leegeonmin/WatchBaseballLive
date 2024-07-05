package com.bongbong.watchbaseball.controller;

import com.bongbong.watchbaseball.dto.GameDto;
import com.bongbong.watchbaseball.dto.GetGamesByName;
import com.bongbong.watchbaseball.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Service
@RequiredArgsConstructor
@RequestMapping("/game")
@Slf4j
public class GameController {
    private final GameService gameService;


    /**
     * 경기일정 검색 API
     *
     * @param teamname 팀이름
     * @return 상대팀이름과 경기날짜로 구성된 리스트
     */
    @Operation(summary = "팀의 경기 일정을 조회")
    @GetMapping
    public ResponseEntity<List<GetGamesByName.Response>> getGamesByName(@RequestParam(name = "teamname")
                                                                        @Schema(description = "허용된 값: LOTTE, DOSAN," +
                                                                                " KIA, SAMSUNG, SSG, NC, LG, KIWOOM, " +
                                                                                "KT, HANWHA", example = "LG")
                                                                        String teamname) {
        log.info("Requesting games for team: {}", teamname);
        List<GameDto> gamesByTeamName = gameService.findGamesByTeamName(teamname);
        return ResponseEntity.ok(gamesByTeamName.stream().map(
                x -> GetGamesByName.Response.builder()
                        .gameDate(x.getGameDate())
                        .oppositionTeam(x.getOppositionTeam()).build()
        ).toList());
    }
}
