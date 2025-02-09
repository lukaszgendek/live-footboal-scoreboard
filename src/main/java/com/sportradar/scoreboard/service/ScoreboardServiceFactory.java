package com.sportradar.scoreboard.service;

public class ScoreboardServiceFactory {

    public static ScoreboardService createScoreboardService() {
        return new ScoreboardServiceImpl();
    }
}