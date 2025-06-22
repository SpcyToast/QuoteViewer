package com.example.quoteviewer.viewmodel

import com.example.quoteviewer.model.Quote
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class QuoteScreenVMTest {

    private val quoteSelector = mock<QuoteSelector>()

    private val viewModel: QuoteScreenVM by lazy {
        QuoteScreenVM(quoteSelector)
    }

    @Test
    fun `newQuote - successful response from fetchQuote`() = runTest {
        mockQuoteSelectorResponse(1)
        viewModel.fetchNewQuote()
        val expected = QuoteScreenState.Presenting(
            quoteEntry = Quote(
                "test quote 1",
                "test author 1",
                "test category 1",
            )
        )
        assertEquals(expected, viewModel.stateFlow.value)
    }

    @Test
    fun `newQuote - unsuccessful response from fetchQuote`() = runTest {
        val exception = RuntimeException("test exception")
        whenever(quoteSelector.fetchQuote()).thenReturn(Result.failure(exception))
        viewModel.fetchNewQuote()
        val expected = QuoteScreenState.Error(
            previousQuote = null,
            errorMessage = "test exception"
        )
        assertEquals(expected, viewModel.stateFlow.value)
    }

    @Test
    fun `viewHistory - history view shows list of quotes from newest to oldest`() = runTest {
        mockQuoteSelectorResponse(1)
        viewModel.fetchNewQuote()
        mockQuoteSelectorResponse(2)
        viewModel.fetchNewQuote()
        mockQuoteSelectorResponse(3)
        viewModel.fetchNewQuote()
        viewModel.viewHistory()

        val expected = QuoteScreenState.History(
            historyQuotes = listOf(
                Quote(
                    "test quote 3",
                    "test author 3",
                    "test category 3",
                ),
                Quote("test quote 2",
                    "test author 2",
                    "test category 2",
                ),
                Quote("test quote 1",
                    "test author 1",
                    "test category 1",
                )
            )
        )
        assertEquals(expected, viewModel.stateFlow.value)
    }

    private suspend fun mockQuoteSelectorResponse(i: Int) {
        whenever(quoteSelector.fetchQuote()).thenReturn(
            Result.success(
                Quote(
                    quote = "test quote $i",
                    author = "test author $i",
                    category = "test category $i",
                )
            )
        )
    }

}
