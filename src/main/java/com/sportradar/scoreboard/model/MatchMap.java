package com.sportradar.scoreboard.model;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class MatchMap {
    private final LinkedHashMap<String, Match> matches = new LinkedHashMap<>();

    public synchronized void put(String key, Function<Match, Match> matchInitializer) {
        matches.put(key, matchInitializer.apply(matches.get(key)));
    }

    public synchronized void remove(String key, Consumer<Match> matchValidator) {
        matchValidator.accept(matches.get(key));
        matches.remove(key);
    }

    public synchronized List<Match> values() {
        return new ArrayList<>(matches.values());
    }
}
