# Weather Test App
This project is a non-commercial Android application created to demonstrate Android development skills, including working with Compose UI, state management, and testing. The project includes a wide range of tests, including unit tests for domain logic and Compose UI tests for screens and components, showcasing best practices for testing Android applications.

# Architecture
The project uses the MVVM architecture with elements of MVI. The UI interacts with the ViewModel through function calls, and the ViewModel exposes state using Flows to update the UI reactively. 

# Features
- Display current, hourly, and daily weather forecasts using Compose UI;
- State management with Kotlin Flows;
- Error handling and retry mechanisms;
- Mock data generation for testing purposes.

# Technologies Used
- Kotlin;
- Coroutines;
- Hilt;
- Jetpack Compose;
- Retrofit
- Compose UI testing;
- MockK;
- JUnit.
