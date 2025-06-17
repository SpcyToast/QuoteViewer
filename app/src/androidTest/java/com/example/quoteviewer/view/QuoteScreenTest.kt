package com.example.quoteviewer.view

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
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

    @Test
    fun displayQuoteOnLaunch(){
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag("quote_content")
                .fetchSemanticsNodes().isNotEmpty()
            composeTestRule
                .onAllNodesWithTag("quote_author")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("quote_content").assertIsDisplayed()
        composeTestRule.onNodeWithTag("quote_author").assertIsDisplayed()
    }

    @Test
    fun newQuoteOnRefresh(){
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag("quote_content")
                .fetchSemanticsNodes().isNotEmpty()
        }
        val initialQuote = composeTestRule.onNodeWithTag("quote_content").fetchSemanticsNode().config.get(SemanticsProperties.Text).joinToString()
        composeTestRule.onNodeWithTag("quote_content").assertTextEquals(initialQuote)
        composeTestRule.onNodeWithTag("new_quote").performClick()
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag("quote_content")
                .fetchSemanticsNodes().isNotEmpty()
        }
        val updatedQuote = composeTestRule.onNodeWithTag("quote_content").fetchSemanticsNode().config.get(SemanticsProperties.Text).joinToString()
        composeTestRule.onNodeWithTag("quote_content").assertTextEquals(updatedQuote)
        assert(initialQuote != updatedQuote)
    }
}