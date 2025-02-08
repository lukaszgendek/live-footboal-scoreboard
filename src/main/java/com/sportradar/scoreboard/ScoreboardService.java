package com.sportradar.scoreboard;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class ScoreboardService {
    private static final String SEPARATOR = "|";
    private final SynchronizedMatchMap map = new SynchronizedMatchMap();

    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        map.put(generateKey(homeTeam, awayTeam), existingMatch -> {
            if (existingMatch != null)
                throw new IllegalArgumentException("Match between these teams is already in progress.");
            return new Match(homeTeam,  0, awayTeam, 0);
        });
    }

    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        validateTeamNames(homeTeam, awayTeam);
        map.put(generateKey(homeTeam, awayTeam), existingMatch -> {
            if (existingMatch == null)
                throw new IllegalArgumentException("Match between these teams does not exist.");
            validateScores(existingMatch, homeScore, awayScore);
            return existingMatch.withHomeScore(homeScore).withAwayScore(awayScore);
        });
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        map.remove(generateKey(homeTeam, awayTeam), existingMatch -> {
            if (existingMatch == null)
                throw new IllegalArgumentException("Match between these teams does not exist.");
        });
    }

    public List<Match> getMatches() {
        return map.getAll();
    }

    public List<Match> getSummary() {
        return getMatches().stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore))
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

}
