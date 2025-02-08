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

    @Given("a scoreboard with multiple matches")
    public void a_scoreboard_with_multiple_matches(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new io.cucumber.java.PendingException();
    }
    @When("I get a summary of matches in progress")
    public void i_get_a_summary_of_matches_in_progress() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the summary should list matches ordered by total score in descending order")
    public void the_summary_should_list_matches_ordered_by_total_score_in_descending_order() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("ties should be broken by the most recently started match")
    public void ties_should_be_broken_by_the_most_recently_started_match() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
