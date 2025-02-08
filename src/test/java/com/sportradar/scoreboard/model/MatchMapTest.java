package com.sportradar.scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class MatchMapTest {
    private MatchMap matchMap;

    @BeforeEach
    public void setUp() {
        matchMap = new MatchMap();
    }

    @Test
    public void testPutAndRemove() {
        String key = "TeamA vs TeamB";
        matchMap.put(key, existingMatch -> new Match("TeamA", 0, "TeamB", 0));
        assertEquals(1, matchMap.getAll().size());

        matchMap.remove(key, existingMatch -> {
        });
        assertTrue(matchMap.getAll().isEmpty());
    }

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        int threadCount = 100;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        AtomicReference<Exception> exceptionRef = new AtomicReference<>();

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    String teamA = "Team" + index;
                    String teamB = "Team" + (index + 1);
                    String key = teamA + " vs " +
                            teamB;
                    matchMap.put(key, existingMatch -> new Match(teamA, index % 10, teamB, (index + 1) % 10));
                    matchMap.remove(key, existingMatch -> {
                    });
                } catch (Exception e) {
                    exceptionRef.set(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        boolean completed = latch.await(30, TimeUnit.SECONDS);
        executorService.shutdown();

        assertTrue(completed, "Timeout occurred before all threads finished");
        assertNull(exceptionRef.get(), "Exception occurred during concurrent operations: " + (exceptionRef.get() != null ? exceptionRef.get().getMessage() : ""));
        assertTrue(matchMap.getAll().isEmpty());
    }
}