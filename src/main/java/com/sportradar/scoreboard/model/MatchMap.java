package com.sportradar.scoreboard.model;

import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.vavr.collection.LinkedHashMap.empty;

public class MatchMap {
    private LinkedHashMap<String, Match> matches = empty();
    public synchronized void put(String key, Function<Option<Match>, Match> matchInitializer) {
        matches = matches.put(key, matchInitializer.apply(matches.get(key)));
    }

    public synchronized void remove(String key, Consumer<Option<Match>> matchValidator) {
        matchValidator.accept(matches.get(key));
        matches = matches.remove(key);
    }

    public synchronized Seq<Match> values() {
        return matches.values();
    }
}
