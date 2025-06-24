package com.example.quoteviewer.view

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quoteviewer.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuoteScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val quoteContent: SemanticsNodeInteraction =
        composeTestRule.onNodeWithTag("quote_content")
    private val quoteAuthor: SemanticsNodeInteraction =
        composeTestRule.onNodeWithTag("quote_author")
    private val newQuoteButton: SemanticsNodeInteraction =
        composeTestRule.onNodeWithTag("new_quote")
    private val loadingState: SemanticsNodeInteraction =
        composeTestRule.onNodeWithTag("loading_state")

    @Test
    fun displayQuoteOnLaunch(){
        waitUntilQuoteDisplays()
        quoteContent.assertIsDisplayed()
        quoteAuthor.assertIsDisplayed()
    }

    @Test
    fun newQuoteOnRefresh(){
        waitUntilQuoteDisplays()
        val initialQuote = quoteContent.fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()
        newQuoteButton.performClick()
        loadingState.assertIsDisplayed()
        waitUntilQuoteDisplays()
        loadingState.assertIsNotDisplayed()
        val updatedQuote = quoteContent.fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()
        assert(initialQuote != updatedQuote)
    }

    private fun waitUntilQuoteDisplays() {
        composeTestRule.waitUntil(5000) {
            quoteContent.isDisplayed()
        }
    }
}
