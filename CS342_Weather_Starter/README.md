# CS 342 Weather Information Service Project

## Team Members
David Flores & Levell Kensey, Team MiniPekka >:)
![mini pekka img](https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/5a6af839-076e-448b-b7e8-47dcfb1f1af3/dgew8g0-3bb40d87-19df-4b09-8c22-e25fa348ff27.png/v1/fill/w_894,h_894/mini_pekka_render_by_henukim_dgew8g0-pre.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDA5NiIsInBhdGgiOiIvZi81YTZhZjgzOS0wNzZlLTQ0OGItYjdlOC00N2RjZmIxZjFhZjMvZGdldzhnMC0zYmI0MGQ4Ny0xOWRmLTRiMDktOGMyMi1lMjVmYTM0OGZmMjcucG5nIiwid2lkdGgiOiI8PTQwOTYifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.5stibFKlkjGePUUkFKQQxKSIbOs6_hfmqVpXoT8QKxY)
## Running the Application
Ensure that you have the requirements of:
- Java 25
- Maven
- JUnit Jupiter
```bash
mvn compile
java -cp target/classes weather.Main
```
Or, if you have an integrated IDE like Intellij or VSCode, navigate to the `Main.java` file and press 'Run'.
## Running the Tests
You can run the tests using the following command
```bash
mvn test
```
And you can view what the tests are actually looking for within the files of the test folder.

## Design
## Interfaces
We use the `WeatherDataProvider` interface, as it gives a blueprint for any weather API class. For example,
it allows us to understand how to format `OpenMeteoWeatherProvider`, and we can also use it for any new weather API.
## Dependency Injection
`WeatherDataProvider` is given to `WeatherService` directly via its constructor. Also, `WeatherService` is given to
`WeatherCLI.` The benefits of dependency injection is that it allows our program to be flexible and not have to hardcode
behavior.
## Testing
Rather than using the API directly, we created fake data that is only for the purpose of testing our tests' functionality.
This allows us to test offline and with data that won't change.
## Java 25
Java 25-specific language features that we take advantage of in our project include Records, Pattern matching (for parsing),
the java.lang package for the IO class. These features helped by making our code more efficient and readable. Patterns were also important
for parsing through the API.
## Additional Feature 
We have included additional functionality where `WeatherService` can return the highest temperature as a record with the
city name and its associated temp. The record allows for any weather value that is a double, so other functions could be written
to find highest/lowest wind speed, or other values as well!

## Design Reflection Questions
### 1) What responsibilities were combined in the original starter code that you separated during refactoring?
 - In the original starter code, there were multiple responsibilities given to the `Main.java` file, including
parsing, requesting info from the API, creating records to store weather data, and interface data. Each of these responsibilities
were separated by us when designing and refactoring the project. We place these reponsibilities in different classes and changed
some of their behavior so it isn't hardcoded to a specific API.
### 2) How does programming to WeatherDataProvider make the program easier to change?
- In the case that we want to use another API or create a mock provider (which we do for testing), having the WeatherDataProvider interface makes it easier by allowing us
to simply make another class that has the same methods in the interface, with optional new methods to fit the needs of the class.
We are already given a blueprint for what our class should look like.
### 3) How does dependency injection make WeatherService easier to test?
- In our code, you can find the class `FakeWeatherDataProvider`, which is what we use as a controlled implementation for testing.
We noticed that dependency injection here comes from giving `WeatherService` its `WeatherDataProvider` through the constructor. This makes it easier
to test becuase, since `WeatherService` depends on a `WeatherDataProvider` to get weather, it can now easily gain access to that, rather than
having to create its own provider and data.
### 4) Why should unit tests avoid depending on the live weather API?
- When having unit tests that depend on an API, the test doesn't just depend on correctness,
but it now can also depend upon internet connection, rate limits, API availability, etc. We just
want to know if our tests are correctly testing what needs to be tested, so we don't need to use the real API to find that out.
We also don't want to deal with specific data so we know what to expect. A live API means these values will change throughout the day which
makes it difficult to test.
### 5) If the current weather API were replaced with a completely different provider, which parts of your application would need to change? Which parts should not need to change?
- If we used a different API, we wouldn't necessarily have to change what is already written, because we have an interface that should be compatible with any provider.
We would just have to create a new class for the new provider which implements our interface, and add any specific methods besides the interface's if needed. If we declare a provider
anywhere, we would need to change that to reflect the new provider.