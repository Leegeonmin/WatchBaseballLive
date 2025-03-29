package com.bongbong.watchbaseball.domain;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class GameTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    boolean isHomeGame;

}
