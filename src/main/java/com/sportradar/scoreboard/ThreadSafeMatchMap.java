package com.sportradar.scoreboard;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class ThreadSafeMatchMap {
    private final LinkedHashMap<String, Match> matches = new LinkedHashMap<>();

    public synchronized void put(String key, Function<Match, Match> validatorAndCreator) {
        matches.put(key, validatorAndCreator.apply(matches.get(key)));
    }

    public synchronized void remove(String key, Consumer<Match> validator) {
        validator.accept(matches.get(key));
        matches.remove(key);
    }

    public synchronized List<Match> getAll() {
        return new ArrayList<>(matches.values());
    }

}
