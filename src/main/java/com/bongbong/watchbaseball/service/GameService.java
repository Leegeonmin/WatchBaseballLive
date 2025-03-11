package com.bongbong.watchbaseball.service;

import com.bongbong.watchbaseball.domain.GameEntity;
import com.bongbong.watchbaseball.dto.GetGameListByTeamNameResponse;
import com.bongbong.watchbaseball.exception.CustomException;
import com.bongbong.watchbaseball.exception.ErrorCode;
import com.bongbong.watchbaseball.repository.GameRepository;
import com.bongbong.watchbaseball.type.TeamName;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

  private final GameRepository gameRepository;


  /**
   * 팀이름으로 경기일정 찾기 팀이름이 유효한지 먼저 검증 후 조회
   *
   * @param teamNameString 팀이름
   * @return 상대팀이름과 경기날짜로 구성된 리스트
   */
  public List<GetGameListByTeamNameResponse> findGamesByTeamName(String teamNameString) {
    TeamName teamName = TeamName.getTeamByString(teamNameString).orElseThrow(
            () -> new CustomException(ErrorCode.TEAM_NOT_FOUND)
    );

    LocalDateTime currentDay = LocalDateTime.now();
    List<GameEntity> gameEntities = gameRepository.findByGameTimeAfterAndHomeTeamOrGameTimeAfterAndAwayTeam(
            currentDay, teamName, currentDay, teamName
    );

    return gameEntities.stream().map(x ->
                    GetGameListByTeamNameResponse.builder()
                    .gameDate(x.getGameTime().toLocalDate())
                    .oppositionTeam(
                            x.getHomeTeam() == teamName ? x.getAwayTeam().name() : x.getHomeTeam().name())
                    .location(x.getLocation())
                    .build()
          ).collect(Collectors.toList());
  }
}
