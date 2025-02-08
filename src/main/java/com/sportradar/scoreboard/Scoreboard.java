package com.sportradar.scoreboard;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class Scoreboard {
    private static final String SEPARATOR = "|";
    private final LinkedHashMap<String, Match> matches = new LinkedHashMap<>();

    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        String key = generateKey(homeTeam, awayTeam);
        if (matches.containsKey(key)) {
            throw new IllegalArgumentException("Match between these teams is already in progress.");
        }
        matches.put(key, new Match(homeTeam,  0, awayTeam, 0));
    }

    public List<Match> getMatches() {
        return new ArrayList<>(matches.values());
    }

    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        validateTeamNames(homeTeam, awayTeam);
        validateScores(homeTeam, homeScore, awayTeam, awayScore);
        String key = generateKey(homeTeam, awayTeam);
        Match match = matches.get(key);
        if (match == null) {
            throw new IllegalArgumentException("Match between these teams does not exist.");
        }
        matches.put(key, match.withHomeScore(homeScore).withAwayScore(awayScore));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        String key = generateKey(homeTeam, awayTeam);
        Match match = matches.get(key);
        if (match == null) {
            throw new IllegalArgumentException("Match between these teams does not exist.");
        }
        matches.remove(key);
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
    private void validateScores(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores must not be negative.");
        }
        String key = generateKey(homeTeam, awayTeam);
        Match match = matches.get(key);
        if (match != null) {
            if (homeScore < match.getHomeScore() || awayScore < match.getAwayScore()) {
                throw new IllegalArgumentException("Scores must not be decreased.");
            }
        }
    }

}
