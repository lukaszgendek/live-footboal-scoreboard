package com.sportradar.scoreboard.service;

public class MatchDto {
    private final String homeTeam;
    private final int homeScore;
    private final String awayTeam;
    private final int awayScore;

    public MatchDto(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public int getTotalScore() {
        return this.homeScore + this.awayScore;
    }
}
