package com.sportradar.scoreboard.service;

import java.util.List;

/**
 * Interface for the scoreboard service that manages match operations.
 */
public interface ScoreboardService {

    /**
     * Creates a new match between the specified home team and away team.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @throws IllegalArgumentException if the match already exists.
     */
    void startMatch(String homeTeam, String awayTeam);

    /**
     * Updates the score of an existing match between the specified home team
     * and away team.
     *
     * @param homeTeam the name of the home team.
     * @param homeScore the new score for the home team.
     * @param awayTeam the name of the away team.
     * @param awayScore the new score for the away team.
     * @throws IllegalArgumentException if the match does not exist.
     */
    void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) ;
    /**
     * Finishes an existing match between the specified home team and away team.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @throws IllegalArgumentException if the match does not exist.
     */
    void finishMatch(String homeTeam, String awayTeam);

    /**
     * Returns a summary of all ongoing matches.
     *
     * @return a list of MatchDto representing the ongoing matches.
     */
    List<MatchDto> getSummary();
}