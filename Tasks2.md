# Quote Viewer App with API Integration

Objective:
Build a simple Android app that fetches and displays quotes from an external API.

# Requirements
## Quote Source
Use the API Ninjas Quotes API.
Sign up to get a free API key.
Fetch a random quote from:
https://api.api-ninjas.com/v1/quotes

## App Structure
Follow MVVM architecture.
Components:
- MainActivity
- SingleQuoteFragment
- SingleQuoteViewModel

## Functionality
- On launch, display one random quote (quote text and author).
- Add a “New Quote” button to fetch another quote from the server.
- Handle loading state (e.g. progress bar) and basic errors (e.g. Snackbar message).

## Technical Requirements
- Use Ktor Client for the API call. (Refer to CreditCardList repo, ktor official website: https://ktor.io/)
- Refactor QuoteSelector, use Dependency Injection to inject instances of network call implementation.
- Use ViewModel and StateFlow to manage UI state.
- Display a fallback message if the API fails.

## Unit Tests
- Write basic unit tests for:
    - ViewModel logic (e.g. quote loading success/failure)
    - QuoteSelector class

# Design
- Simple, readable UI using Jetpack Compose.
- Clearly display the quote and author, centered on screen.

# README file
Update README.md file with:
- Overview of the project
- Setup/run instructions
- Summary of dependencies and tools used
- Any known issues or limitations
