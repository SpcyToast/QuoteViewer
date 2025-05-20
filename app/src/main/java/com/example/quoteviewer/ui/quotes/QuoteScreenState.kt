package com.example.quoteviewer.ui.quotes

import com.example.quoteviewer.domain.Quote

interface QuoteScreenState {
    data class Presenting(
        val quoteEntry: Quote
    ): QuoteScreenState
    data class History(
        val history: List<Quote>
    ): QuoteScreenState
}
