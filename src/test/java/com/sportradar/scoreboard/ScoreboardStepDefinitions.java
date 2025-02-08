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
        assertEquals(score, first.getHomeTeam() + " " + first.getHomeScore() +
                " - " + first.getAwayTeam() + " " + first.getAwayScore());
    }

    @Given("a scoreboard with one match {string}")
    public void a_scoreboard_with_one_match(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("I update the score of the match to {string}")
    public void i_update_the_score_of_the_match_to(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the scoreboard should show the updated score {string}")
    public void the_scoreboard_should_show_the_updated_score(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
