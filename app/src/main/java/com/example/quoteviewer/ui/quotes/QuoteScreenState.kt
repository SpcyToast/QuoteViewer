package com.example.quoteviewer.ui.quotes

import com.example.quoteviewer.domain.model.Quote

interface QuoteScreenState {
    data class Presenting(
        val quoteEntry: Quote
    ): QuoteScreenState
    data class History(
        val historyQuotes: List<Quote>
    ): QuoteScreenState
}
