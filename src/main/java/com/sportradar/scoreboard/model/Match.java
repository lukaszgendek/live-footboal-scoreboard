package com.sportradar.scoreboard.model;

public class Match {

    private final String homeTeam;
    private final int homeScore;
    private final String awayTeam;
    private final int awayScore;

    public Match(String homeTeam, int homeScore, String awayTeam, int awayScore) {
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

    public Match withHomeScore(int homeScore) {
        if (homeScore == this.homeScore)
            return this;
        return new Match(this.homeTeam, homeScore, this.awayTeam, this.awayScore);
    }

    public Match withAwayScore(int awayScore) {
        if (awayScore == this.awayScore)
            return this;
        return new Match(this.homeTeam, this.homeScore, this.awayTeam, awayScore);
    }

    public int getTotalScore() {
        return this.homeScore + this.awayScore;
    }
}