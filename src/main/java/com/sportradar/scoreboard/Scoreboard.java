package com.sportradar.scoreboard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Scoreboard {
    private static final String SEPARATOR = "|";
    private final LinkedHashMap<String, Match> matches = new LinkedHashMap<>();

    public void startMatch(String homeTeam, String awayTeam) {
        matches.put(getKey(homeTeam, awayTeam), new Match(homeTeam,  0, awayTeam, 0));
    }

    public List<Match> getMatches() {
        return new ArrayList<>(matches.values());
    }

    public void updateMatch(String homeTeam, int homeScore, String awayTeam, int awayScore) {
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

}
