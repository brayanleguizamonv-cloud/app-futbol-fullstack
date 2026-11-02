package com.appfutbol.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PartidoApiDto {

    private String utcDate;
    private CompetitionDto competition;
    private ScoreDto score;
    private EquipoDto homeTeam;
    private EquipoDto awayTeam;

    public String getUtcDate() { return utcDate; }
    private Integer matchday;
    public Integer getMatchday() { return matchday; }
    public void setMatchday(Integer matchday) { this.matchday = matchday; }
    public void setUtcDate(String utcDate) { this.utcDate = utcDate; }
    public CompetitionDto getCompetition() { return competition; }
    public void setCompetition(CompetitionDto competition) { this.competition = competition; }
    public ScoreDto getScore() { return score; }
    public void setScore(ScoreDto score) { this.score = score; }
    public EquipoDto getHomeTeam() { return homeTeam; }
    public void setHomeTeam(EquipoDto homeTeam) { this.homeTeam = homeTeam; }
    public EquipoDto getAwayTeam() { return awayTeam; }
    public void setAwayTeam(EquipoDto awayTeam) { this.awayTeam = awayTeam; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CompetitionDto {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EquipoDto {
        private String name;
        private String crest;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCrest() { return crest; }
        public void setCrest(String crest) { this.crest = crest; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ScoreDto {
        private FullTimeDto fullTime;
        public FullTimeDto getFullTime() { return fullTime; }
        public void setFullTime(FullTimeDto fullTime) { this.fullTime = fullTime; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FullTimeDto {
        private Integer home;
        private Integer away;
        public Integer getHome() { return home; }
        public void setHome(Integer home) { this.home = home; }
        public Integer getAway() { return away; }
        public void setAway(Integer away) { this.away = away; }
    }
}