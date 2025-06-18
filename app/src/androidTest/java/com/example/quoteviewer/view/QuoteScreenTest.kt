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

    private val QUOTE_CONTENT = "quote_content"
    private val QUOTE_AUTHOR = "quote_author"
    private val NEW_QUOTE = "new_quote"

    fun loadContent(){
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(QUOTE_CONTENT)
                .fetchSemanticsNodes().isNotEmpty()
            composeTestRule
                .onAllNodesWithTag(QUOTE_AUTHOR)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun displayQuoteOnLaunch(){
        loadContent()
        composeTestRule.onNodeWithTag(QUOTE_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(QUOTE_AUTHOR).assertIsDisplayed()
    }

    @Test
    fun newQuoteOnRefresh(){
        loadContent()
        val initialQuote = composeTestRule.onNodeWithTag(QUOTE_CONTENT).fetchSemanticsNode().config.get(SemanticsProperties.Text).joinToString()
        composeTestRule.onNodeWithTag(QUOTE_CONTENT).assertTextEquals(initialQuote)
        composeTestRule.onNodeWithTag(NEW_QUOTE).performClick()
        loadContent()
        val updatedQuote = composeTestRule.onNodeWithTag(QUOTE_CONTENT).fetchSemanticsNode().config.get(SemanticsProperties.Text).joinToString()
        composeTestRule.onNodeWithTag(QUOTE_CONTENT).assertTextEquals(updatedQuote)
        assert(initialQuote != updatedQuote)
    }
}