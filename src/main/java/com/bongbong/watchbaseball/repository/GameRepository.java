package com.bongbong.watchbaseball.repository;

import com.bongbong.watchbaseball.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    @Query("SELECT g FROM game g WHERE g.location LIKE %:location% AND DATE(g.gameTime) = :targetDate")
    GameEntity findByLocationAndDate(@Param("location") String location, @Param("targetDate") LocalDate targetDate);

}
