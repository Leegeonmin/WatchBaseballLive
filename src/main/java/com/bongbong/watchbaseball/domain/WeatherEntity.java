package com.bongbong.watchbaseball.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity(name = "weather")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "min_temperature", nullable = false)
    private double minTemperature;

    @Column(name = "max_temperature", nullable = false)
    private double maxTemperature;

    @Column(name = "precipitation_probability", nullable = false)
    private int precipitationProbability;

    @ManyToOne
    @JoinColumn(name = "stadium_id", nullable = false)
    private StadiumEntity stadium;

    public void update(double minTemp, double maxTemp, int precipitation, String description) {
        this.minTemperature = minTemp;
        this.maxTemperature = maxTemp;
        this.precipitationProbability = precipitation;
        this.description = description;
    }
}
