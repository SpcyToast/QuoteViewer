package com.example.quoteviewer.viewmodel

import com.example.quoteviewer.model.Quote
import kotlinx.coroutines.test.runTest
import okhttp3.internal.concurrent.Task
import okhttp3.internal.wait
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class QuoteScreenVMTest{

    private val quoteSelector = mock<QuoteSelector>()

    private val viewModel: QuoteScreenVM by lazy {
        QuoteScreenVM(quoteSelector)
    }

    suspend fun initialiseQuote(i: Int){
        whenever(quoteSelector.fetchQuote()).thenReturn(Result.success(Quote("test quote ${i}","test author ${i}","test category ${i}")))
        viewModel.newQuote().join()
    }

    @Test
    fun `newQuote - successful response from fetchQuote`() = runTest{
        initialiseQuote(1)
        assertEquals(QuoteScreenState.Presenting(quoteEntry = Quote("test quote 1","test author 1","test category 1"), errorMessage = null), viewModel.stateFlow.value)
    }

    @Test
    fun `newQuote - unsuccessful response from fetchQuote`() = runTest{
        initialiseQuote(0)
        val exception = RuntimeException("test exception")
        whenever(quoteSelector.fetchQuote()).thenReturn(Result.failure(exception))
        viewModel.newQuote().join()
        assertEquals(QuoteScreenState.Presenting(quoteEntry = Quote("test quote 0","test author 0","test category 0"), errorMessage = "test exception"), viewModel.stateFlow.value)
    }

    @Test
    fun `viewHistory - history view shows list of quotes from newest to oldest`() = runTest{
        initialiseQuote(1)
        initialiseQuote(2)
        initialiseQuote(3)
        viewModel.viewHistory()
        assertEquals(QuoteScreenState.History(historyQuotes = listOf<Quote>(Quote("test quote 3","test author 3","test category 3"), Quote("test quote 2","test author 2","test category 2"), Quote("test quote 1","test author 1","test category 1"))), viewModel.stateFlow.value)
    }

}