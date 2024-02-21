# Danamon Frontend Test

This application was created as an implementation example for an Android application that meets the basic needs of a developer in developing applications using Kotlin and Android Studio. This application has login, register, main page features, and has different roles between admin and user.

## Key Features

- Login: Users can sign in to the application using valid credentials.
- Register: Users can register to create a new account.
- Main Page: The main page displays content according to the user's role, The application has different access and display between admin and user.

## Technologies and Libraries Used

1. Kotlin: The programming language used in developing the application.
2. Android Studio: Integrated Development Environment (IDE) for Android.
3. Hilt Dagger: Used for dependency management.
4. MVVM (Model-View-ViewModel): Architectural design pattern used to separate business logic from the view.
5. Room: SQLite database library for Android.
6. Retrofit: HTTP client library for communication with the backend.
7. Chuck Logging: Facilitates logging and debugging in the development process.
8. Coroutine: Manages asynchronous operations easily and efficiently.
9. Glide: Library for loading and displaying images easily and efficiently.
10. Material UI: UI component library that provides a set of pre-designed components following Material Design guidelines.

## Architecture and Project Structure

This project uses modular architecture to separate the parts of the application. Below is the project structure used:

- app
    - src
        - main
            - java/com/danamon/danamonfrontendtest
                - di
                - ui
    - res
- core
    - src
        - main
            - java/com/danamon/core
                - base
                - component
                - ext
                - util
    - res
- data
    - src
        - main
            - java/com/danamon/data
                - api
                    - {example}
                        - model
                        - repository
                - di
                - dispatcher
                - implementation
                    - {example}
                        - local
                        - mapper
                        - remote
                        - repository
- feature
    - src
        - main
            - java/com/danamon/feature
                - di
                - navigator
                - ui

## How to Run the Project

1. Make sure you have Android Studio installed on your computer.
2. Clone this repository to your local machine.
3. Open the project using Android Studio.
4. Synchronize the project if needed.
5. Make sure you have an emulator or connected physical device to run the application.
6. Run the project by clicking the "Run" button in Android Studio.