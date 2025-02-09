package com.sportradar.scoreboard.service;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.MatchMap;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

/**
 * Service class to manage scoreboard operations such as creating, updating,
 * and finishing matches. Returns a summary of all ongoing matches
 */
public class ScoreboardService {
    private static final String SEPARATOR = "|";
    private final MatchMap map = new MatchMap();

    /**
     * Creates a new match between the specified home team and away team.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @throws IllegalArgumentException if the match already exists.
     */
    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        map.put(generateKey(homeTeam, awayTeam), existingMatchOpt -> {
            if (existingMatchOpt.isPresent()) {
                throw new IllegalArgumentException("Match between these teams is already in progress.");
            }
            return new Match(homeTeam, 0, awayTeam, 0);
        });
    }

    /**
     * Updates the score of an existing match between the specified home team
     * and away team.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @param homeScore the new score for the home team.
     * @param awayScore the new score for the away team.
     * @throws IllegalArgumentException if the match does not exist.
     */
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        validateTeamNames(homeTeam, awayTeam);
        map.put(generateKey(homeTeam, awayTeam), existingMatchOpt -> {
            Match existingMatch = existingMatchOpt.orElseThrow(() -> new IllegalArgumentException("Match between these teams does not exist."));
            validateScores(existingMatch, homeScore, awayScore);
            return existingMatch.withHomeScore(homeScore).withAwayScore(awayScore);
        });
    }

    /**
     * Finishes an existing match between the specified home team and away team.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @throws IllegalArgumentException if the match does not exist.
     */
    public void finishMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        map.remove(generateKey(homeTeam, awayTeam), existingMatchOpt -> {
            if (!existingMatchOpt.isPresent()) {
                throw new IllegalArgumentException("Match between these teams does not exist.");
            }
        });
    }

    public List<MatchDto> getMatches() {
        return map.values().stream()
                .map(ScoreboardService::mapMatch)
                .collect(Collectors.toList());
    }

    /**
     * Returns a summary of all ongoing matches.
     *
     * @return a list of MatchDto representing the ongoing matches.
     */
    public List<MatchDto> getSummary() {
        return map.values().stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore))
                .map(ScoreboardService::mapMatch)
                .collect(
                        collectingAndThen(
                                // Since LinkedHashMap maintains insertion order, we only need to reverse the list
                                Collectors.toList(),
                                l -> {
                                    Collections.reverse(l);
                                    return l;
                                }
                        )
                );
    }

    private static String generateKey(String homeTeam, String awayTeam) {
        return homeTeam + SEPARATOR + awayTeam;
    }

    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null || homeTeam.trim().isEmpty() || awayTeam.trim().isEmpty()) {
            throw new IllegalArgumentException("Team names must not be null or empty.");
        }
    }

    private void validateScores(Match match, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores must not be negative.");
        }
        if (match != null) {
            if (homeScore < match.getHomeScore() || awayScore < match.getAwayScore()) {
                throw new IllegalArgumentException("Scores must not be decreased.");
            }
        }
    }

    private static MatchDto mapMatch(Match match) {
        return new MatchDto(match.getHomeTeam(), match.getHomeScore(), match.getAwayTeam(), match.getAwayScore());
    }

}
