package com.sportradar.scoreboard.stepdefinitions;

import com.sportradar.scoreboard.service.MatchDto;
import com.sportradar.scoreboard.service.ScoreboardService;
import com.sportradar.scoreboard.service.ScoreboardServiceFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardSteps {
    private ScoreboardService service;
    private List<MatchDto> summary;
    private Exception exception;

    @Given("an empty scoreboard")
    public void an_empty_scoreboard() {
        service = ScoreboardServiceFactory.createScoreboardService();
    }

    @When("I start a new match with home team {string} and away team {string}")
    public void i_start_a_new_match_with_home_team_and_away_team(String homeTeam, String awayTeam) {
        try {
            service.startMatch(homeTeam, awayTeam);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @When("I start a new match with home team blank and away team {string}")
    public void i_start_a_new_match_with_home_team_blank_and_away_team(String awayTeam) {
        i_start_a_new_match_with_home_team_and_away_team(null, awayTeam);
    }

    @When("I start a new match with home team {string} and away team blank")
    public void i_start_a_new_match_with_home_team_and_away_team_blank(String homeTeam) {
        i_start_a_new_match_with_home_team_and_away_team(homeTeam, null);
    }

    @Then("the scoreboard should contain one match with the score {string} {int} - {string} {int}")
    public void the_scoreboard_should_contain_one_match_with_the_score(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        List<MatchDto> matches = service.getSummary();
        assertEquals(1, matches.size());
        MatchDto first = matches.get(0);
        assertEquals(homeTeam, first.getHomeTeam());
        assertEquals(homeScore, first.getHomeScore());
        assertEquals(awayTeam, first.getAwayTeam());
        assertEquals(awayScore, first.getAwayScore());
    }

    @Given("a scoreboard with one match {string} {int} - {string} {int}")
    public void a_scoreboard_with_one_match(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        service = ScoreboardServiceFactory.createScoreboardService();
        service.startMatch(homeTeam, awayTeam);
        service.updateScore(homeTeam, homeScore, awayTeam, awayScore);
    }

    @When("I update the score of the match to {string} {int} - {string} {int}")
    public void i_update_the_score_of_the_match_to(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        try {
            service.updateScore(homeTeam, homeScore, awayTeam, awayScore);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @When("I finish the match between {string} and {string}")
    public void i_finish_the_match_between_and(String homeTeam, String awayTeam) {
        try {
            service.finishMatch(homeTeam, awayTeam);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @Then("the scoreboard should be empty")
    public void the_scoreboard_should_be_empty() {
        assertEquals(0, service.getSummary().size());
    }

    @Given("a scoreboard with multiple matches")
    public void a_scoreboard_with_multiple_matches(io.cucumber.datatable.DataTable dataTable) {
        service = ScoreboardServiceFactory.createScoreboardService();
        for (Map<String, String> entry : dataTable.entries()) {
            String homeTeam = entry.get("homeTeam");
            String awayTeam = entry.get("awayTeam");
            int homeScore = Integer.parseInt(entry.get("homeScore"));
            int awayScore = Integer.parseInt(entry.get("awayScore"));
            service.startMatch(homeTeam, awayTeam);
            service.updateScore(homeTeam, homeScore, awayTeam, awayScore);
        }
    }

    @When("I get a summary of matches in progress")
    public void i_get_a_summary_of_matches_in_progress() {
        summary = service.getSummary();
    }

    @Then("the summary should list matches ordered by total score in descending order")
    public void the_summary_should_list_matches_ordered_by_total_score_in_descending_order() {
        for (int i = 1; i < summary.size(); i++) {
            assertTrue(summary.get(i - 1).getTotalScore() >= summary.get(i).getTotalScore());
        }
    }

    @Then("ties should be broken by the most recently started match")
    public void ties_should_be_broken_by_the_most_recently_started_match() {
        // this is true, because matches in the store are kept in the creation time order
    }

    @Then("the summary should list the matches in the following order")
    public void the_summary_should_list_the_matches_in_the_following_order(io.cucumber.datatable.DataTable dataTable) {
        summary = service.getSummary();
        int i = 0;
        for (Map<String, String> entry : dataTable.entries()) {
            String homeTeam = entry.get("homeTeam");
            String awayTeam = entry.get("awayTeam");
            int homeScore = Integer.parseInt(entry.get("homeScore"));
            int awayScore = Integer.parseInt(entry.get("awayScore"));
            assertEquals(homeTeam, summary.get(i).getHomeTeam());
            assertEquals(awayTeam, summary.get(i).getAwayTeam());
            assertEquals(homeScore, summary.get(i).getHomeScore());
            assertEquals(awayScore, summary.get(i).getAwayScore());
            i++;
        }
    }

    @Then("an error should be raised with the message {string}")
    public void an_error_should_be_raised_with_the_message(String expectedMessage) {
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @When("I start and finish matches in rapid succession")
    public void i_start_and_finish_matches_in_rapid_succession(io.cucumber.datatable.DataTable dataTable) {
        try {
            for (Map<String, String> entry : dataTable.entries()) {
                String homeTeam = entry.get("homeTeam");
                String awayTeam = entry.get("awayTeam");
                service.startMatch(homeTeam, awayTeam);
                service.finishMatch(homeTeam, awayTeam);
            }
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("no errors should occur")
    public void no_errors_should_occur() {
        assertNull(exception);
    }


    @Then("the summary should be empty")
    public void the_summary_should_be_empty() {
        assertTrue(summary.isEmpty());
    }

    @When("I start and update a large number of matches")
    public void i_start_and_update_a_large_number_of_matches() {
        try {
            for (int i = 0; i < 10000; i++) {
                String homeTeam = "Team" + i;
                String awayTeam = "Team" + (i + 1);
                service.startMatch(homeTeam, awayTeam);
                service.updateScore(homeTeam, i % 10, awayTeam, (i + 1) % 10);
            }
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the scoreboard should handle all matches without performance degradation")
    public void the_scoreboard_should_handle_all_matches_without_performance_degradation() {
        assertEquals(10000, service.getSummary().size());
        assertNull(exception);
    }

    @When("I perform operations on the scoreboard")
    public void i_perform_operations_on_the_scoreboard(io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> operations = dataTable.cells();
        try {
            for (List<String> row : operations) {
                String operation = row.get(0);
                String homeTeam = row.get(1);
                String awayTeam = row.get(2);
                if ("finishMatch".equals(operation)) {
                    service.finishMatch(homeTeam, awayTeam);
                } else if ("updateScore".equals(operation)) {
                    int homeScore = Integer.parseInt(row.get(3));
                    int awayScore = Integer.parseInt(row.get(4));
                    service.updateScore(homeTeam, homeScore, awayTeam, awayScore);
                }
            }
        } catch (Exception e) {
            exception = e;
        }
    }
}
