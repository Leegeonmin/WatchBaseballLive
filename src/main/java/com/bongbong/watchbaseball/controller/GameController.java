package com.bongbong.watchbaseball.controller;

import com.bongbong.watchbaseball.dto.GetGameListByTeamNameResponse;
import com.bongbong.watchbaseball.exception.CustomErrorResponse;
import com.bongbong.watchbaseball.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success",
          content = {
              @Content(array = @ArraySchema(schema = @Schema(implementation = GetGameListByTeamNameResponse.class)))}),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = {
              @Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
  })
  public ResponseEntity<List<GetGameListByTeamNameResponse>> getGamesByName(
      @RequestParam(name = "teamname")
      @Schema(description = "허용된 값: LOTTE, DOSAN," +
          " KIA, SAMSUNG, SSG, NC, LG, KIWOOM, " +
          "KT, HANWHA", example = "LG")
      String teamname) {
    return ResponseEntity.ok(gameService.findGamesByTeamName(teamname));
  }
}
