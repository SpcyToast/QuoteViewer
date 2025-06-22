package com.example.quoteviewer.viewmodel

import kotlinx.coroutines.test.runTest
import com.example.quoteviewer.model.QuoteClient
import com.example.quoteviewer.model.Quote
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuoteSelectorTest {
    private val quoteClient = mock<QuoteClient>()
    private val quoteSelector by lazy {
        QuoteSelector(quoteClient)
    }

    @Test
    fun `fetchQuote - get quote from API call - success`() = runTest {
        whenever(quoteClient.getQuote()).thenReturn(
            Quote(
                "Test Quote",
                "Test Author",
                "Test Category"
            )
        )
        assertEquals(
            quoteSelector.fetchQuote(),
            Result.success(Quote("Test Quote", "Test Author", "Test Category"))
        )
    }

    @Test
    fun `fetchQuote - get quote from API call - failure`() = runTest {
        val sampleException = RuntimeException("test")
        whenever(quoteClient.getQuote()).thenThrow(sampleException)

        val quoteResult = quoteSelector.fetchQuote()
        assertTrue(quoteResult.isFailure)
        assertEquals(sampleException, quoteResult.exceptionOrNull())
    }
}
