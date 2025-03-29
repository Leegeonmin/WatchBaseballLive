package com.bongbong.watchbaseball.repository;

import com.bongbong.watchbaseball.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

//  List<GameEntity> findByGameTimeAfterAndHomeTeamOrGameTimeAfterAndAwayTeam(LocalDateTime dateTime1,
//      TeamName homeTeam,
//      LocalDateTime dateTime2,
//      TeamName awayTeam);
}
