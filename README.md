# Danamon Frontend Test

This application was created as an implementation example for an Android application that meets the basic needs of a developer in developing applications using Kotlin and Android Studio. This application has login, register, main page features, and has different roles between admin and user.

## Key Features

- Login: Users can sign in to the application using valid credentials.
- Register: Users can register to create a new account.
- Main Page: The main page displays content according to the user's role, The application has different access and display between admin and user.
    - admin page : show all user and can manage user (edit and delete user)
    - user page : show all data form https://jsonplaceholder.typicode.com/photos and i used paging and limit

## Technologies and Libraries Used

1. Kotlin
2. Android Studio
3. Hilt Dagger
4. MVVM (Model-View-ViewModel)
5. Room
6. Retrofit
7. Chuck Logging
8. Coroutine
9. Glide
10. Material UI

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

## Preview Apps
apk test
: https://drive.google.com/file/d/1P_uFe319f3zIPzGleGKmQiAMAtXjTQk3/view?usp=drive_link

![Login Page](https://firebasestorage.googleapis.com/v0/b/kanzankazu-itung2an.appspot.com/o/project%2F1709049440219.jpg?alt=media&token=4096f55e-c942-47f6-8f3b-3fef32c26d72)
![Register Page](https://firebasestorage.googleapis.com/v0/b/kanzankazu-itung2an.appspot.com/o/project%2F1709049440217.jpg?alt=media&token=f3b82764-de19-4f53-8c57-e4f10da4aeb1)
![Admin Page](https://firebasestorage.googleapis.com/v0/b/kanzankazu-itung2an.appspot.com/o/project%2F1709049440215.jpg?alt=media&token=108fa706-66b9-44d0-88b9-0321d7d9ffd7)
![User Page](https://firebasestorage.googleapis.com/v0/b/kanzankazu-itung2an.appspot.com/o/project%2F1709049440212.jpg?alt=media&token=4dad9c54-2f53-491e-aca6-7cc22a6b21ce)