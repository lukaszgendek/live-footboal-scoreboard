# Scoreboard Application

## Description
The Scoreboard Application is designed to manage and display the scores of various matches. It allows you to start a new match, update scores, finish a match, and get a summary of all ongoing matches. The application is designed with thread safety in mind to handle concurrent operations seamlessly.

## Features
- Start a new match
- Update scores of an ongoing match
- Finish an ongoing match
- Get a summary of all ongoing matches, sorted by total score in descending order

## Design
The application is structured into three main components:
1. **Model**: Contains the data structures for matches and a thread-safe map to manage them.
2. **Service**: Contains the business logic for managing matches.
3. **DTO (Data Transfer Object)**: Represents the data that is transferred between the service layer and the client.

### Model
- **Match**: Represents a match between two teams with their respective scores.
- **MatchMap**: A thread-safe map that manages matches. It ensures that all operations on the map are synchronized.

### Service
- **ScoreboardService**: Contains the business logic for creating, updating, and finishing matches. It uses `MatchMap` to manage the matches.

### DTO
- **MatchDto**: Represents the data that is exposed to the client. It includes the home team, away team, home score, and away score.

# Acceptance Criteria

# Acceptance Criteria

| Scenario | Initial Scenario | Action | Expected Result |
|----------|------------------|--------|-----------------|
| Start a new match | Empty scoreboard | Start a new match with home team ```Mexico``` and away team ```Canada``` | The scoreboard should contain one match, with the score ```Mexico``` ```0``` - ```Canada``` ```0```. |
| Update the score of a match | Scoreboard with one match (```Mexico``` ```0``` - ```Canada``` ```0```) | Update the score of the match to ```Mexico 3 - Canada 2``` | The scoreboard should show the updated score ```Mexico 3 - Canada 2```. |
| Finish a match | Scoreboard with one match (```Mexico``` ```3``` - ```Canada``` ```2```) | Finish the match | The scoreboard should be empty. |
| Get summary of matches | Scoreboard with multiple matches (e.g., ```Mexico``` ```0``` - ```Canada``` ```5```, ```Spain``` ```10``` - ```Brazil``` ```2```) | Get a summary of matches in progress | The summary should list matches ordered by total score in descending order, with ties broken by most recently started match. |
| Start and update a new match | Scoreboard with multiple matches (e.g., ```Mexico``` ```0``` - ```Canada``` ```5```, ```Spain``` ```10``` - ```Brazil``` ```2```, ```Germany``` ```2``` - ```France``` ```2```) | Start a new match with home team ```Uruguay``` and away team ```Italy``` and update the score to ```Uruguay``` ```6``` - ```Italy``` ```6``` | The scoreboard should list the matches with ```Uruguay``` ```6``` - ```Italy``` ```6``` at the top, followed by ```Spain``` ```10``` - ```Brazil``` ```2```, ```Mexico``` ```0``` - ```Canada``` ```5```, and ```Germany``` ```2``` - ```France``` ```2```. |
| Finish a specific match | Scoreboard with multiple matches (e.g., ```Uruguay``` ```6``` - ```Italy``` ```6```, ```Spain``` ```10``` - ```Brazil``` ```2```) | Finish the match between ```Spain``` and ```Brazil``` | The scoreboard should list the remaining match ```Uruguay``` ```6``` - ```Italy``` ```6```. |
| Finish a non-existent match | Empty scoreboard | Try to finish a match between ```Mexico``` and ```Canada``` | An error should be thrown indicating that the match between these teams does not exist. |
| Start a duplicate match | Scoreboard with one match (```Mexico``` ```0``` - ```Canada``` ```0```) | Try to start another match between ```Mexico``` and ```Canada``` | An error should be thrown indicating that a match between these teams is already in progress. |
| Update a non-existent match | Scoreboard with multiple matches (e.g., ```Mexico``` ```0``` - ```Canada``` ```5```, ```Spain``` ```10``` - ```Brazil``` ```2```) | Try to update the score of a non-existent match between ```Germany``` and ```France``` | An error should be thrown indicating that the match between these teams does not exist. |
| Update score with negative values | Scoreboard with one match (```Mexico``` ```0``` - ```Canada``` ```0```) | Try to update the score to negative values (e.g., ```Mexico``` ```-3``` - ```Canada``` ```2```) | An error should be thrown indicating that scores must not be negative. |
| Start a match with invalid team names | Empty scoreboard | Try to start a match with empty or null team names | An error should be thrown indicating that team names must not be null or empty. |
| Update score to zero | Scoreboard with one match (```Mexico``` ```3``` - ```Canada``` ```2```) | Try to update the score to zero (e.g., ```Mexico``` ```0``` - ```Canada``` ```0```) | An error should be thrown indicating that scores must not be decreased. |
| Handle large number of matches | Empty scoreboard | Start and update scores for a large number of matches (e.g., 10,000 matches) | The scoreboard should handle all operations without performance issues or errors. |
| Perform operations on empty scoreboard | Empty scoreboard | Try to finish or update a match on an empty scoreboard | Errors should be thrown indicating that the match between the specified teams does not exist. |
| Concurrent operations | Empty scoreboard | Perform multiple operations (start, update, finish) on matches concurrently | The scoreboard should handle all operations correctly and maintain data integrity. |