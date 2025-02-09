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

## Acceptance Test-Driven Development (ATDD)
The application was developed using the Acceptance Test-Driven Development (ATDD) methodology. ATDD is a collaborative approach that involves the following steps:

1. **Define Acceptance Criteria**: The team collaboratively defines the acceptance criteria for each feature. These criteria serve as the basis for the acceptance tests.

2. **Create Cucumber Skeletons**: For each acceptance criterion, Cucumber skeletons were created. These skeletons define the Given-When-Then structure for the acceptance tests.

3. **Implement the Feature**: The feature is implemented to pass the acceptance tests. This involves writing the necessary code in the model, service, and DTO layers.

4. **Refine Acceptance Criteria**: After implementing the main features, edge case acceptance criteria were formulated to handle special scenarios and ensure robustness.

5. **Refactor and Optimize**: The code is refactored and optimized to ensure maintainability, performance, and readability.

## Step-by-Step Process

1. **Define Initial Acceptance Criteria**:
   The initial acceptance criteria were defined to cover the basic functionality of the scoreboard. These criteria are documented in the [acceptance_criteria.md](./acceptance_criteria.md) file.

2. **Create Cucumber Skeletons**:
   For each acceptance criterion, a corresponding Cucumber skeleton was created in the `ScoreboardSteps.java` file. These skeletons define the Given-When-Then structure for the tests.

3. **Implement the Feature**:
   The features were implemented to pass the acceptance tests. The implementation involved creating the necessary classes and methods in the `model`, `service`, and `dto` packages.

4. **Formulate Edge Case Acceptance Criteria**:
   After implementing the main features, additional acceptance criteria were formulated to handle edge cases and special scenarios. These criteria were added to the [acceptance_criteria.md](./acceptance_criteria.md) file.

5. **Develop for Edge Cases**:
   The application was further developed to handle the edge cases defined in the acceptance criteria. This involved updating the implementation and adding new tests.

6. **Refactor and Optimize**:
   The code was refactored and optimized to ensure maintainability, performance, and readability. This included improving the design, removing redundancies, and enhancing performance.

## Acceptance Criteria

The following table [table](./acceptance_criteria.md) lists the acceptance criteria used for developing the application.

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
- JDK 11 or higher
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

## Usage
### Starting a Match
To start a new match, use the createMatch method:

```java
ScoreboardService service = new ScoreboardService();
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
List<MatchDto> summary = service.getMatches();
for (MatchDto match : summary) {
    System.out.println(match);
}
```


