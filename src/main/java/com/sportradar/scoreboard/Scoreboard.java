package com.sportradar.scoreboard;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class Scoreboard {
    private static final String SEPARATOR = "|";
    private final LinkedHashMap<String, Match> matches = new LinkedHashMap<>();

    public void startMatch(String homeTeam, String awayTeam) {
        matches.put(getKey(homeTeam, awayTeam), new Match(homeTeam,  0, awayTeam, 0));
    }

    public List<Match> getMatches() {
        return new ArrayList<>(matches.values());
    }

    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        String key = getKey(homeTeam, awayTeam);
        Match match = matches.get(key);
        if (match == null) {
            throw new IllegalArgumentException("Match does not exist " + homeTeam + " vs " + awayTeam);
        } else {
            matches.put(key, match.withHomeScore(homeScore).withAwayScore(awayScore));
        }
    }

    private static String getKey(String homeTeam, String awayTeam) {
        return homeTeam + SEPARATOR + awayTeam;
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        String key = getKey(homeTeam, awayTeam);
        Match match = matches.get(key);
        if (match == null) {
            throw new IllegalArgumentException("Match does not exist " + homeTeam + " vs " + awayTeam);
        } else {
            matches.remove(key);
        }
    }

    public List<Match> getSummary() {
        return getMatches().stream()
                .sorted(Comparator.comparingInt(m -> m.getHomeScore() + m.getAwayScore()))
                .collect(
                        collectingAndThen(
                                Collectors.toList(),
                                l -> {
                                    Collections.reverse(l);
                                    return l;
                                }
                        )
                );
    }
}
