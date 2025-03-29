package com.bongbong.watchbaseball.domain;

import jakarta.persistence.*;


@Entity
public class GameTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

}
