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

| Initial Scenario | Action | Expected Result |
|------------------|--------|-----------------|
| Empty scoreboard | Start a new match with home team ```Mexico``` and away team ```Canada``` | The scoreboard should contain one match, with the score ```Mexico``` ```0``` - ```Canada``` ```0```. |
| Scoreboard with one match (```Mexico``` ```0``` - ```Canada``` ```0```) | Update the score of the match to ```Mexico 3 - Canada 2``` | The scoreboard should show the updated score ```Mexico 3 - Canada 2```. |
| Scoreboard with one match (```Mexico``` ```3``` - ```Canada``` ```2```) | Finish the match | The scoreboard should be empty. |
| Scoreboard with multiple matches (e.g., ```Mexico``` ```0``` - ```Canada``` ```5```, ```Spain``` ```10``` - ```Brasil``` ```2```) | Get a summary of matches in progress | The summary should list matches ordered by total score in descending order, with ties broken by most recently started match. |
| Scoreboard with multiple matches (e.g., ```Mexico``` ```0``` - ```Canada``` ```5```, ```Spain``` ```10``` - ```Brasil``` ```2```, ```Germany``` ```2``` - ```France``` ```2```) | Start a new match with home team ```Uruguay``` and away team ```Italy``` and update the score to ```Uruguay``` ```6``` - ```Italy``` ```6``` | The scoreboard should list the matches with ```Uruguay``` ```6``` - ```Italy``` ```6``` at the top, followed by ```Spain``` ```10``` - ```Brasil``` ```2```, ```Mexico``` ```0``` - ```Canada``` ```5```, and ```Germany``` ```2``` - ```France``` ```2```. |
| Scoreboard with multiple matches (e.g., ```Uruguay``` ```6``` - ```Italy``` ```6```, ```Spain``` ```10``` - ```Brasil``` ```2```) | Finish the match between ```Spain``` and ```Brazil``` | The scoreboard should list the remaining match ```Uruguay``` ```6``` - ```Italy``` ```6```. |
