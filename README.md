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
-- **ScoreboardService Interface**
   - Defines the contract for the scoreboard service.
   - Ensures that any implementation of the scoreboard service adheres to the specified methods.
   - Allows for easy swapping of different implementations without changing the client code.
-- **ScoreboardServiceImpl Class**
   - Implements the ```ScoreboardService``` interface.
   - Provides concrete implementations for managing matches, including creating, updating, and finishing matches.
   - Uses a ```MatchMap``` to store and manage match data.
-- **ScoreboardServiceFactory Class**
   - Provides a method to create instances of the ```ScoreboardService``` interface.
   - Encapsulates the creation logic, allowing for easy modification of the instantiation process.
   - Promotes the use of the interface rather than the implementation class directly.

#### Advantages
- **Separation of Concerns**: The interface defines the contract, while the implementation class provides the concrete behavior. The factory encapsulates the instantiation logic.
- **Flexibility**: Easily swap out the implementation of the ```ScoreboardService``` without changing the client code.
- **Testability**: Facilitate unit testing by allowing mock implementations of the ```ScoreboardService``` interface.
- **Maintainability**: Clear separation of responsibilities makes the codebase easier to understand and maintain.

### DTO
- **MatchDto**: Represents the data that is exposed to the client. It includes the home team, away team, home score, and away score.

## Acceptance Test-Driven Development (ATDD)
The application was developed using the Acceptance Test-Driven Development (ATDD) methodology. ATDD is a collaborative approach that involves the following steps:

1. **Define Acceptance Criteria**: The team collaboratively defines the acceptance criteria for each feature. These criteria serve as the basis for the acceptance tests.

2. **Create Cucumber Skeletons**: For each acceptance criterion, Cucumber skeletons were created. These skeletons define the Given-When-Then structure for the acceptance tests.

3. **Implement the Feature**: The feature is implemented to pass the acceptance tests. This involves writing the necessary code in the model, service, and DTO layers.

4. **Refine Acceptance Criteria**: After implementing the main features, edge case acceptance criteria were formulated to handle special scenarios and ensure robustness.

5. **Refactor and Optimize**: The code is refactored and optimized to ensure maintainability, performance, and readability.

## Step-by-Step Process

1. **Define Initial Acceptance Criteria**:
   The initial acceptance criteria were defined to cover the basic functionality of the scoreboard. These criteria are documented in the [scoreboard.feature](./scoreboard.feature) file written in Gherkin language and in this README.md file.

2. **Create Cucumber Skeletons**:
   For each acceptance criterion, a corresponding Cucumber skeleton was created in the `ScoreboardSteps.java` file. These skeletons define the Given-When-Then structure for the tests.

3. **Implement the Feature**:
   The features were implemented to pass the acceptance tests. The implementation involved creating the necessary classes and methods in the `model`, `service`, and `dto` packages.

4. **Formulate Edge Case Acceptance Criteria**:
   After implementing the main features, additional acceptance criteria were formulated to handle edge cases and special scenarios. These criteria were added to the [scoreboard.feature](./scoreboard.feature) file and here to the README.md file.
   
6. **Develop for Edge Cases**:
   The application was further developed to handle the edge cases defined in the acceptance criteria. This involved updating the implementation and adding new tests.

7. **Refactor and Optimize**:
   The code was refactored and optimized to ensure maintainability, performance, and readability. This included improving the design, removing redundancies, and enhancing performance.

## Acceptance Criteria

The following table lists the acceptance criteria used for developing the application:

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

## Vavr Immutable Collections

### Use of Vavr's Immutable `LinkedHashMap`
In an alternative implementation branch named [immutability_provided_by_vavr](https://github.com/lukaszgendek/live-footboal-scoreboard/compare/main...immutability_provided_by_vavr), Vavr's immutable `LinkedHashMap` is utilized to manage the matches. This approach simplifies the code and potentially improves performance by avoiding the need for copying the collection when returning from synchronized methods. Immutable collections inherently provide thread safety, which ensures that the internal state cannot be modified unexpectedly.

#### Benefits:
1. **Thread Safety**: Immutable collections naturally provide thread safety as they cannot be modified once created.
2. **No Need for Copying**: Unlike mutable collections, there is no need to create a copy of the collection when returning it from synchronized methods.

#### Example Implementation:
```java name=src/main/java/com/sportradar/scoreboard/model/MatchMap.java
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.vavr.collection.LinkedHashMap.empty;

public class MatchMap {
    private LinkedHashMap<String, Match> matches = empty();

    public synchronized void put(String key, Function<Option<Match>, Match> matchInitializer) {
        matches = matches.put(key, matchInitializer.apply(matches.get(key)));
    }

    public synchronized void remove(String key, Consumer<Option<Match>> matchValidator) {
        matchValidator.accept(matches.get(key));
        matches = matches.remove(key);
    }

    public synchronized Seq<Match> values() {
        return matches.values();
    }
}
````
### Conclusion
Although Vavr's immutable collections offer several benefits, they are not as widely adopted as Java's standard collections. Therefore, this implementation is provided in a separate branch for experimentation and evaluation. Feel free to explore this branch and consider the trade-offs before integrating it into the main branch.

## How to Start
### Prerequisites
- JDK 8 or higher
- Maven 3.6.3 or higher

### Running the Application
1. Clone the repository:
    ```sh
    git clone https://github.com/lukaszgendek/live-footboal-scoreboard.git
    ```
2. Navigate to the project directory:
    ```sh
    cd live-footboal-scoreboard
    ```
3. Build the project using Maven:
    ```sh
    mvn clean install
    ```
4. Run the tests:
    ```sh
    mvn test
    ```
### Running the tests using docker-compose

Use Docker Compose to build and run your tests in the defined environment.

1. Build and run the containers
```sh
docker-compose up --build
```
2. Run the tests inside the app container
```sh
docker exec -it my-app mvn test
```

Self-contained tests run in a consistent environment every time. This approach eliminates issues related to differences in development environments and makes it easier to reproduce test results.

## Usage
### Starting a Match
To start a new match, use the createMatch method:

```java
ScoreboardService service = ScoreboardServiceFactory.createScoreboardService();
service.createMatch("TeamA", "TeamB");
```

### Updating Scores
To update the scores of an ongoing match, use the updateScore method:

```java
service.updateScore("TeamA", "TeamB", 3, 2);
```

### Finishing a Match
To finish an ongoing match, use the finishMatch method:

```java
service.finishMatch("TeamA", "TeamB");
```

### Getting Match Summary
To get a summary of all ongoing matches, use the getMatches method:

```java
List<MatchDto> summary = service.getSummary();
for (MatchDto match : summary) {
    System.out.println(match);
}
```


