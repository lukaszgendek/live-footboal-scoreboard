Feature: Live Football World Cup Scoreboard

  Scenario: Start a new match
    Given an empty scoreboard
    When I start a new match with home team "Mexico" and away team "Canada"
    Then the scoreboard should contain one match with the score "Mexico" 0 - "Canada" 0

  Scenario: Update the score of an ongoing match
    Given a scoreboard with one match "Mexico" 0 - "Canada" 0
    When I update the score of the match to "Mexico" 3 - "Canada" 2
    Then the scoreboard should contain one match with the score "Mexico" 3 - "Canada" 2

  Scenario: Finish an ongoing match
    Given a scoreboard with one match "Mexico" 3 - "Canada" 2
    When I finish the match with home team "Mexico" and away team "Canada"
    Then the scoreboard should be empty
