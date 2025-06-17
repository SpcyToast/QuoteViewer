Write UI tests using androidx.compose.ui.test to verify key behaviors.

## Test Setup
- Use androidx.compose.ui.test package
- Set up Compose test environment in androidTest/
- If you're not sure how to do, there are so many UI test examples to refer to in https://stash.westpac.co.nz/projects/WONE/repos/android/browse/app/src/androidTestDemoDevDebug/java/nz/co/westpac/one

## Test Cases
### Scenario 1: Quote is Displayed on Launch
- Launch MainActivity
- Assert the quote text and author are visible and not empty

### Scenario 2: Refresh Button Loads a New Quote
- Click the refresh button
- Assert the quote text changes

