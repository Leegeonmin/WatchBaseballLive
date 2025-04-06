package com.bongbong.watchbaseball.repository;

import com.bongbong.watchbaseball.domain.StadiumEntity;
import com.bongbong.watchbaseball.domain.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherEntityRepository extends JpaRepository<WeatherEntity, Long> {
    Optional<WeatherEntity> findByStadiumAndDate(StadiumEntity stadium, LocalDate date);
}
