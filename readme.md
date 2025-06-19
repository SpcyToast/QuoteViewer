# Project Overview
This project was created to grasp the basics of android development using Jetpack Compose. 
It consists of a single fragment that either displays a random quote provided by an API or shows a history of all previously seen quotes from the session.

# Setup and Run instructions
- Clone this repository from github to your local device.
- open the project in android studio.
- sync gradle files by either clicking sync now or by clicking the elephant with a downwards diagonal arrow icon located in the top right.
- Build the app by clicking into Build menu and then Assemble 'app' Run configuration.
- Set up a device emulator by clicking on the tools menu, then Device Manager. Add a new device by clicking the plus symbol and follow on screen instructions.
- Run the app using your desired emulator by either clicking on the run menu and then clicking Run 'app'.
- The app should launch in the emulator ready for use

# Dependancies and Tools
### - Jetpack Compose
Jetpack compose is a UI toolkit that allows for creating modular interfaces with the help of composable functions. These functions are display using view models and fragments that allow for responsive and context based views. Composable functions allow for UI to be made in a way that descriptive and easy to follow. This projects UI made completely using composable functions

### - Dagger Hilt
Dagger is an injection framework that automates dependency management on android apps. Hilt is a dependency library that removes a lot of the boilerplate code required to use Dagger. By using Hilt, you remove a lot of manual dependency injection meaning that extra classes with separate dependence don't need to be made which makes the code modular and easier to maintain and test. Dagger Hilt was used in this project to send API data to the viewmodel.

### - Ktor
Ktor is a kotlin based framework used to build and use asynchronous network applications on both the frontend and the backend. For the purposes of this project, it was used in conjunction with okhttp to make API call which supplied the app with quotes to show to the user.

### - JUnit
JUnit is a testing framework used to write automated unit tests. I allows developers to check whether or not their classes, functions, viewmodel, or UI are working as intended without having to manually test everything on code updates. In this project JUnit was used for all (class, viewmodel, and UI) unit tests.

### - Mockito
Mockito is a mocking framework used for unit testing. It is used to mock dependencies to produce predictable data that can be used in unit tests to see whether or not functions are working correctly. In this project it is used for viewmodel and class unit tests.

# Issues and Limitations
- Occasionally a 403 error occurs and devices that use proxy. This caused be a unauthorised API call and will prevent the app from showing a quote on launch. The UI test will also fail when this occurs. While the error has occur several times, it it unclear how to fix the issue but it does seem to start working after a while.
