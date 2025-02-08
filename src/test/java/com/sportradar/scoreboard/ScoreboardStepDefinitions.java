package com.sportradar.scoreboard;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreboardStepDefinitions {
    private Scoreboard scoreboard;

    @Given("an empty scoreboard")
    public void an_empty_scoreboard() {
        scoreboard = new Scoreboard();
    }

    @When("I start a new match with home team {string} and away team {string}")
    public void i_start_a_new_match_with_home_team_and_away_team(String homeTeam, String awayTeam) {
        scoreboard.startMatch(homeTeam, awayTeam);
    }

    @Then("the scoreboard should contain one match with the score {string}")
    public void the_scoreboard_should_contain_one_match_with_the_score(String score) {
        List<Match> matches = scoreboard.getSummary();
        assertEquals(1, matches.size());
        Match first = matches.get(0);
        String homeTeam = first.getHomeTeam();
        String awayTeam = first.getAwayTeam();
        int homeScore = first.getHomeScore();
        int awayScore = first.getAwayScore();
        assertEquals(score,homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore);
    }

}
