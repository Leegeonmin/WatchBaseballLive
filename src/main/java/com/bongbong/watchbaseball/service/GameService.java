package com.bongbong.watchbaseball.service;

import com.bongbong.watchbaseball.domain.GameEntity;
import com.bongbong.watchbaseball.domain.GameTeam;
import com.bongbong.watchbaseball.domain.TeamEntity;
import com.bongbong.watchbaseball.dto.GameScheduleDTO;
import com.bongbong.watchbaseball.exception.CustomException;
import com.bongbong.watchbaseball.exception.ErrorCode;
import com.bongbong.watchbaseball.repository.GameRepository;
import com.bongbong.watchbaseball.repository.GameTeamRepository;
import com.bongbong.watchbaseball.repository.TeamRepository;
import com.bongbong.watchbaseball.type.TeamName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

  private final GameRepository gameRepository;
  private final GameTeamRepository gameTeamRepository;
  private final TeamRepository teamRepository;

//  /**
//   * 팀이름으로 경기일정 찾기 팀이름이 유효한지 먼저 검증 후 조회
//   *
//   * @param teamNameString 팀이름
//   * @return 상대팀이름과 경기날짜로 구성된 리스트
//   */
//  public List<GetGameListByTeamNameResponse> findGamesByTeamName(String teamNameString) {
//    TeamName teamName = TeamName.getTeamByString(teamNameString).orElseThrow(
//            () -> new CustomException(ErrorCode.TEAM_NOT_FOUND)
//    );
//
//    LocalDateTime currentDay = LocalDateTime.now();
//    List<GameEntity> gameEntities = gameRepository.findByGameTimeAfterAndHomeTeamOrGameTimeAfterAndAwayTeam(
//            currentDay, teamName, currentDay, teamName
//    );
//
//    return gameEntities.stream().map(x ->
//            GetGameListByTeamNameResponse.builder()
//                    .gameDate(x.getGameTime().toLocalDate())
//                    .oppositionTeam(
//                            "oppositionTeam")
//                    .location(x.getLocation())
//                    .build()
//    ).collect(Collectors.toList());
//  }


    // v2
    public List<GameScheduleDTO> findGamesByTeamNamev2(String teamNameString) {
        // application
        String teamName = TeamName.getKoreanNameByTeamName(teamNameString).orElseThrow(
                () -> new CustomException(ErrorCode.TEAM_NOT_FOUND)
        );
        // db
        TeamEntity team = teamRepository.findByName(teamName).orElseThrow(
                () -> new CustomException(ErrorCode.TEAM_NOT_FOUND)
        );

        List<GameTeam> gameTeamList = gameTeamRepository.findByTeamAndGameGameTimeAfter(team.getName(), LocalDateTime.now());


        // 경기 일정 정보를 DTO로 변환하여 반환
        return gameTeamList.stream()
                .map(gameTeam -> {
                    GameEntity game = gameTeam.getGame();

                    // 상대 팀을 찾기 위해 다른 GameTeam 객체를 찾음
                    GameTeam opponentGameTeam = game.getGameTeams().stream()
                            .filter(gt -> !gt.getTeam().equals(team))  // 자기 팀이 아닌 GameTeam 찾기
                            .findFirst()
                            .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

                    // 상대 팀을 가져오기
                    TeamEntity opponent = opponentGameTeam.getTeam();

                    // 홈/원정 여부에 따라 구장 정보를 설정
                    String stadiumName;
                    if (gameTeam.isHomeGame()) {
                        stadiumName = team.getStadium().getName(); // 홈 팀의 구장
                    } else {
                        stadiumName = opponent.getStadium().getName(); // 원정 팀의 구장
                    }

                    return new GameScheduleDTO(
                            game.getGameTime(),          // 게임 시간
                            game.getLocation(),          // 경기 위치
                            opponent.getName(),          // 상대 팀
                            gameTeam.isHomeGame(),       // 홈 경기 여부
                            stadiumName                  // 구장 이름
                    );
                })
                .collect(Collectors.toList());
    }
}