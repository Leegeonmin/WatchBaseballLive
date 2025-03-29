package com.bongbong.watchbaseball.domain;



import com.bongbong.watchbaseball.type.TeamName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "game")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class GameEntity {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private LocalDateTime gameTime;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<GameTeam> gameTeams;

    @NotBlank
    @Column(length = 20)
    private String location;

    @LastModifiedDate
    private LocalDateTime updatedDate;
    @CreatedDate
    private LocalDateTime createdDate;
}
