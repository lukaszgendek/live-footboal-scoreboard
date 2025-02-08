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

    @Then("the scoreboard should contain one match with the score {string} {int} - {string} {int}")
    public void the_scoreboard_should_contain_one_match_with_the_score(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        List<Match> matches = scoreboard.getMatches();
        assertEquals(1, matches.size());
        Match first = matches.get(0);
        assertEquals(first.getHomeTeam(), homeTeam);
        assertEquals(first.getHomeScore(), homeScore);
        assertEquals(first.getAwayTeam(), awayTeam);
        assertEquals(first.getAwayScore(), awayScore);
    }

    @Given("a scoreboard with one match {string} {int} - {string} {int}")
    public void a_scoreboard_with_one_match(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        scoreboard = new Scoreboard();
        scoreboard.startMatch(homeTeam, awayTeam);
        scoreboard.updateMatch(homeTeam, homeScore, awayTeam, awayScore);
    }

    @When("I update the score of the match to {string} {int} - {string} {int}")
    public void i_update_the_score_of_the_match_to(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        scoreboard.updateMatch(homeTeam, homeScore, awayTeam, awayScore);
    }

    @When("I finish the match with home team {string} and away team {string}")
    public void i_finish_the_match_with_home_team_and_away_team(String homeTeam, String awayTeam) {
        scoreboard.finishMatch(homeTeam, awayTeam);
    }

    @Then("the scoreboard should be empty")
    public void the_scoreboard_should_be_empty() {
        assertEquals(0, scoreboard.getMatches().size());
    }
}
