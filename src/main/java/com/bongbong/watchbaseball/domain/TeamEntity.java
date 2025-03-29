package com.bongbong.watchbaseball.domain;

import com.bongbong.watchbaseball.domain.StadiumEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "team")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private StadiumEntity stadium;

    @OneToMany(mappedBy = "team",fetch =FetchType.LAZY)
    private List<GameTeam> gameTeams;

    @OneToOne
    @JoinColumn(name = "weather_id")
    private WeatherEntity weather;

    @NotBlank
    @Column(length = 20)
    private String name;

    @LastModifiedDate
    private LocalDateTime updatedDate;
    @CreatedDate
    private LocalDateTime createdDate;
}
