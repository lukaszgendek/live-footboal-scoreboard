package com.sportradar.scoreboard.model;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class MatchMap {
    private final LinkedHashMap<String, Match> matches = new LinkedHashMap<>();

    public synchronized void put(String key, Function<Optional<Match>, Match> matchInitializer) {
        matches.put(key, matchInitializer.apply(ofNullable(matches.get(key))));
    }

    public synchronized void remove(String key, Consumer<Optional<Match>> matchValidator) {
        matchValidator.accept(ofNullable(matches.get(key)));
        matches.remove(key);
    }

    public synchronized List<Match> values() {
        return new ArrayList<>(matches.values());
    }
}
