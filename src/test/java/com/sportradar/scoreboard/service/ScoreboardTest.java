package com.sportradar.scoreboard.service;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreboardTest {
    @Test
    public void testThreadSafety() throws InterruptedException {
        ScoreboardService scoreboard = new ScoreboardService();
        int threadCount = 100;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        AtomicReference<Exception> exceptionRef = new AtomicReference<>();

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    String homeTeam = "Team" + index;
                    String awayTeam = "Team" + (index + 1);
                    scoreboard.startMatch(homeTeam, awayTeam);
                    scoreboard.updateScore(homeTeam, index % 10, awayTeam, (index + 1) % 10);
                    scoreboard.getSummary(); // Include read operation
                    scoreboard.finishMatch(homeTeam, awayTeam);
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
        assertTrue(scoreboard.getSummary().isEmpty());
    }
}