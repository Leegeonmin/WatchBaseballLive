package com.bongbong.watchbaseball.repository;

import com.bongbong.watchbaseball.domain.GameEntity;
import com.bongbong.watchbaseball.type.TeamName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findByHomeTeamOrAwayTeam(TeamName teamName1, TeamName teamName2);
}
