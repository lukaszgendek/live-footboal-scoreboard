Feature: Live Football World Cup Scoreboard

  Scenario: Start a new match
    Given an empty scoreboard
    When I start a new match with home team "Mexico" and away team "Canada"
    Then the scoreboard should contain one match with the score "Mexico 0 - Canada 0"

