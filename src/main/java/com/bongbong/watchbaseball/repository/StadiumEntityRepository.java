package com.bongbong.watchbaseball.repository;

import com.bongbong.watchbaseball.domain.StadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumEntityRepository extends JpaRepository<StadiumEntity, Long> {
}
