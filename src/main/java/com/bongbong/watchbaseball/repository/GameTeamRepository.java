package com.bongbong.watchbaseball.repository;

import com.bongbong.watchbaseball.domain.GameTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameTeamRepository extends JpaRepository<GameTeam, Long> {
    @Query("SELECT gt FROM GameTeam gt JOIN FETCH gt.game g JOIN FETCH gt.team t JOIN FETCH t.stadium s " +
            "WHERE t.name = :teamName AND g.gameTime > :currentTime")
    List<GameTeam> findByTeamAndGameGameTimeAfter(@Param("teamName") String teamName, @Param("currentTime") LocalDateTime currentTime);
}
