# Assignment: Quote Viewer App
Goal:
Build a simple Android app that displays an inspirational quote. The quote should stay the same each day unless the user manually refreshes it.

This assignment helps you learn the basic Android architecture: Activity, Fragment, ViewModel, and how to manage state cleanly.

# Requirements:
- Activity:
Create a MainActivity that hosts a single Fragment.
- Fragment:
Create a QuoteFragment that contains:
  - A TextView to show the quote.
  - A TextView to show the author of the quote.
  - A Button to get a new random quote.
- ViewModel:
Use a QuoteViewModel to:
  - Provide the quote of the day (based on the current date).
  - Provide a method to generate a random new quote when the user taps the button.
- Quote logic:
  - Use a local list of hardcoded quotes, the list must contain more than 20 quotes.
  - The quote of the day should be selected using the current date mapped to a list index: `val index = LocalDate.now().toEpochDay().toInt() % quotes.size`

- Gradle:
Use Gradle to add necessary dependencies (e.g. for ViewModel, LiveData).

# Bonus (optional):
- Add a RecyclerView to show a history of viewed quotes.