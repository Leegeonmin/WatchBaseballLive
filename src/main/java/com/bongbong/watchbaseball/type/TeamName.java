package com.bongbong.watchbaseball.type;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum TeamName {
    LOTTE("롯데", "Lotte Giants"),
    DOOSAN("두산", "Doosan Bears"),
    KIA("KIA", "KIA Tigers"),
    SAMSUNG("삼성", "Samsung Lions"),
    SSG("SSG", "SSG Landers"),
    NC("NC", "NC Dinos"),
    LG("LG", "LG Twins"),
    KIWOOM("키움", "Kiwoom Heroes"),
    KT("KT", "KT Wiz"),
    HANWHA("한화", "Hanwha Eagles");
    private final String koreanName;
    private final String englishName;

    TeamName(String koreanName, String englishName) {
        this.koreanName = koreanName;
        this.englishName = englishName;
    }

    public static Optional<TeamName> getTeamByString(String name) {
        for (TeamName team : TeamName.values()) {
            if (team.name().equalsIgnoreCase(name)) {
                return Optional.of(team);
            }
        }
        return Optional.empty();  // 일치하는 팀이 없을 경우
    }
    public static Optional<String> getKoreanNameByTeamName(String teamName) {
        for (TeamName team : TeamName.values()) {
            if (team.name().equalsIgnoreCase(teamName)) {
                return Optional.of(team.getKoreanName());
            }
        }
        return Optional.empty(); // 일치하는 팀이 없을 경우
    }



}
