package com.sportradar.scoreboard.service;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.MatchMap;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class ScoreboardServiceImpl implements ScoreboardService {
    private static final Logger logger = Logger.getLogger(ScoreboardServiceImpl.class.getName());
    private static final String SEPARATOR = "|";
    private final MatchMap map = new MatchMap();

    public void startMatch(String homeTeam, String awayTeam) {
        logger.log(Level.FINER, "Match between {0} and {1} is being started ...",
                new Object[]{homeTeam, awayTeam});
        validateTeamNames(homeTeam, awayTeam);
        map.put(generateKey(homeTeam, awayTeam), existingMatchOpt -> {
            if (existingMatchOpt.isPresent()) {
                throw new IllegalArgumentException("Match between these teams is already in progress.");
            }
            return new Match(homeTeam, 0, awayTeam, 0);
        });
        logger.log(Level.FINER, "Match between {0} and {1} started.",
                new Object[]{homeTeam, awayTeam});
    }

    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        logger.log(Level.FINER, "Match between {0} and {1} is being updated. Score {2}:{3} ...",
                new Object[]{homeTeam, awayTeam, homeScore, awayScore});
        validateTeamNames(homeTeam, awayTeam);
        map.put(generateKey(homeTeam, awayTeam), existingMatchOpt -> {
            Match existingMatch = existingMatchOpt.orElseThrow(() -> new IllegalArgumentException("Match between these teams does not exist."));
            validateScores(existingMatch, homeScore, awayScore);
            return existingMatch.withHomeScore(homeScore).withAwayScore(awayScore);
        });
        logger.log(Level.FINER, "Match between {0} and {1} updated. Score {2}:{3}",
                new Object[]{homeTeam, awayTeam, homeScore, awayScore});

    }

     public void finishMatch(String homeTeam, String awayTeam) {
        logger.log(Level.FINER, "Match between {0} and {1} is being finished ...",
                new Object[]{homeTeam, awayTeam});
        validateTeamNames(homeTeam, awayTeam);
        map.remove(generateKey(homeTeam, awayTeam), existingMatchOpt -> {
            if (!existingMatchOpt.isPresent()) {
                throw new IllegalArgumentException("Match between these teams does not exist.");
            }
        });
        logger.log(Level.FINER, "Match between {0} and {1} finished.",
                new Object[]{homeTeam, awayTeam});
    }

    public List<MatchDto> getSummary() {
        logger.log(Level.FINER, "Getting the summary of ongoing matches.");
        return map.values().stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore))
                .map(ScoreboardServiceImpl::mapMatch)
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
