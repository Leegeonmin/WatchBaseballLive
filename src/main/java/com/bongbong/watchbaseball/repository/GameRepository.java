package com.bongbong.watchbaseball.repository;

import com.bongbong.watchbaseball.domain.GameEntity;
import com.bongbong.watchbaseball.type.TeamName;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

//  List<GameEntity> findByGameTimeAfterAndHomeTeamOrGameTimeAfterAndAwayTeam(LocalDateTime dateTime1,
//      TeamName homeTeam,
//      LocalDateTime dateTime2,
//      TeamName awayTeam);
}
