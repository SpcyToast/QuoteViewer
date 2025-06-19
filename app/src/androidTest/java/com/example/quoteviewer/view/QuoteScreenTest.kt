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

    fun loadConent(){
        composeTestRule.waitUntil(5000) {
            quoteContent.isDisplayed()
        }
    }

    @Test
    fun displayQuoteOnLaunch(){
        loadConent()
        quoteContent.assertIsDisplayed()
        quoteAuthor.assertIsDisplayed()
    }

    @Test
    fun newQuoteOnRefresh(){
        loadConent()
        val initialQuote = quoteContent.fetchSemanticsNode().config.get(SemanticsProperties.Text).joinToString()
        newQuoteButton.performClick()
        // TODO: better to check loading UI shows and disappears instead
        // If the API call fails it will redisplay the last valid quote, but I'll still add the check
        loadingState.assertIsDisplayed()
        loadConent()
        loadingState.assertIsNotDisplayed()
        val updatedQuote = quoteContent.fetchSemanticsNode().config.get(SemanticsProperties.Text).joinToString()
        assert(initialQuote != updatedQuote)
    }
}