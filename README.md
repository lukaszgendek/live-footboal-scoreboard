# Description
This project is a simple library that manages a live football World Cup scoreboard. It allows you to start a new match, update the score, finish a match, and get a summary of matches in progress ordered by their total score.

# Features

1. Start a new match with an initial score of 0-0.
2. Update the score of an ongoing match.
3. Finish a match and remove it from the scoreboard.
4. Get a summary of matches in progress, ordered by total score. Matches with the same total score are ordered by the most recently started match.

# Acceptance Criteria

| Initial Scenario | Action | Expected Result |
|------------------|--------|-----------------|
| Empty scoreboard | Start a new match with home team “Mexico” and away team “Canada” | The scoreboard should contain one match, with the score “Mexico 0 - Canada 0”. |
| Scoreboard with one match (“Mexico 0 - Canada 0”) | Update the score of the match to “Mexico 3 - Canada 2” | The scoreboard should show the updated score “Mexico 3 - Canada 2”. |
| Scoreboard with one match (“Mexico 3 - Canada 2”) | Finish the match | The scoreboard should be empty. |
| Scoreboard with multiple matches (e.g., “Mexico 0 - Canada 5”, “Spain 10 - Brazil 2”) | Get a summary of matches in progress | The summary should list matches ordered by total score in descending order, with ties broken by most recently started match. |
| Scoreboard with multiple matches (e.g., “Mexico 0 - Canada 5”, “Spain 10 - Brazil 2”, “Germany 2 - France 2”) | Start a new match with home team “Uruguay” and away team “Italy” and update the score to “Uruguay 6 - Italy 6” | The scoreboard should list the matches with “Uruguay 6 - Italy 6” at the top, followed by “Spain 10 - Brazil 2”, “Mexico 0 - Canada 5”, and “Germany 2 - France 2”. |
| Scoreboard with multiple matches (e.g., “Uruguay 6 - Italy 6”, “Spain 10 - Brazil 2”) | Finish the match between “Spain” and “Brazil” | The scoreboard should list the remaining match “Uruguay 6 - Italy 6”. |
