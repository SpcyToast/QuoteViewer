package com.example.quoteviewer.viewmodel

import com.example.quoteviewer.model.Quote

interface QuoteScreenState {
    data object Loading: QuoteScreenState
    data class Presenting(
        val quoteEntry: Quote,
        val isLoading: Boolean
    ): QuoteScreenState
    data class History(
        val historyQuotes: List<Quote>
    ): QuoteScreenState
}