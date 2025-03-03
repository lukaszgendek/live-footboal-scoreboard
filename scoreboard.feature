Feature: Live Football World Cup Scoreboard

  Scenario: Start a new match
    Given an empty scoreboard
    When I start a new match with home team "Mexico" and away team "Canada"
    Then the scoreboard should contain one match with the score "Mexico 0 - Canada 0"

  Scenario: Update the score of an ongoing match
    Given a scoreboard with one match "Mexico 0 - Canada 0"
    When I update the score of the match to "Mexico 3 - Canada 2"
    Then the scoreboard should show the updated score "Mexico 3 - Canada 2"

  Scenario: Finish an ongoing match
    Given a scoreboard with one match "Mexico 3 - Canada 2"
    When I finish the match
    Then the scoreboard should be empty

  Scenario: Get a summary of matches in progress
    Given a scoreboard with multiple matches
      | homeTeam | awayTeam | homeScore | awayScore |
      | Mexico   | Canada   | 0         | 5         |
      | Spain    | Brazil   | 10        | 2         |
    When I get a summary of matches in progress
    Then the summary should list matches ordered by total score in descending order
      And ties should be broken by the most recently started match

  Scenario: Start a new match and update score
    Given a scoreboard with multiple matches
      | homeTeam | awayTeam | homeScore | awayScore |
      | Mexico   | Canada   | 0         | 5         |
      | Spain    | Brazil   | 10        | 2         |
      | Germany  | France   | 2         | 2         |
    When I start a new match with home team "Uruguay" and away team "Italy"
      And I update the score to "Uruguay 6 - Italy 6"
    Then the scoreboard should list the matches in the following order
      | homeTeam | awayTeam | homeScore | awayScore |
      | Uruguay  | Italy    | 6         | 6         |
      | Spain    | Brazil   | 10        | 2         |
      | Mexico   | Canada   | 0         | 5         |
      | Germany  | France   | 2         | 2         |

  Scenario: Finish a specific match
    Given a scoreboard with multiple matches
      | homeTeam | awayTeam | homeScore | awayScore |
      | Uruguay  | Italy    | 6         | 6         |
      | Spain    | Brazil   | 10        | 2         |
    When I finish the match between "Spain" and "Brazil"
    Then the scoreboard should list the remaining match
      | homeTeam | awayTeam | homeScore | awayScore |
      | Uruguay  | Italy    | 6         | 6         |

# Edge cases
  Scenario: Start a match with the same teams that already have an ongoing match
    Given a scoreboard with one match "Mexico 0 - Canada 0"
    When I start a new match with home team "Mexico" and away team "Canada"
    Then an error should be raised with the message "Match between these teams is already in progress."

  Scenario: Update the score of a non-existent match
    Given an empty scoreboard
    When I update the score of the match to "Mexico 3 - Canada 2"
    Then an error should be raised with the message "Match between these teams does not exist."

  Scenario: Finish a non-existent match
    Given an empty scoreboard
    When I finish the match between "Mexico" and "Canada"
    Then an error should be raised with the message "Match between these teams does not exist."

  Scenario: Handling invalid team names
    Given an empty scoreboard
    When I start a new match with home team "" and away team "Canada"
    Then an error should be raised with the message "Team names must not be null or empty."
    
    When I start a new match with home team "Mexico" and away team ""
    Then an error should be raised with the message "Team names must not be null or empty."
    
    When I start a new match with home team null and away team "Canada"
    Then an error should be raised with the message "Team names must not be null or empty."
    
    When I start a new match with home team "Mexico" and away team null
    Then an error should be raised with the message "Team names must not be null or empty."

  Scenario: Starting and finishing matches in rapid succession
    Given an empty scoreboard
    When I start and finish matches in rapid succession
      | homeTeam | awayTeam |
      | Mexico   | Canada   |
      | Spain    | Brazil   |
      | Germany  | France   |
    Then the scoreboard should be empty
    And no errors should occur

  Scenario: Update the score with negative values
    Given a scoreboard with one match "Mexico 0 - Canada 0"
    When I update the score of the match to "Mexico -3 - Canada 2"
    Then an error should be raised with the message "Scores must not be negative."

  Scenario: Handle a large number of matches
    Given an empty scoreboard
    When I start and update a large number of matches
    Then the scoreboard should handle all matches without performance degradation

  Scenario: Update the score to zero
    Given a scoreboard with one match "Mexico 3 - Canada 2"
    When I update the score of the match to "Mexico 0 - Canada 0"
    Then an error should be raised with the message "Scores must not be decreased."

  Scenario: Get summary of an empty scoreboard
    Given an empty scoreboard
    When I get a summary of matches in progress
    Then the summary should be empty
    And no errors should occur

  Scenario: Perform operations on an empty scoreboard
    Given an empty scoreboard
    When I perform operations on the scoreboard
      | operation    | homeTeam | awayTeam | homeScore | awayScore |
      | finishMatch  | Mexico   | Canada   |           |           |
      | updateScore  | Mexico   | Canada   | 3         | 2         |
    Then an error should be raised with the message "Match between these teams does not exist."