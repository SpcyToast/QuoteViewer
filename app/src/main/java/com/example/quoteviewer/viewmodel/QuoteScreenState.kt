package com.example.quoteviewer.viewmodel

import com.example.quoteviewer.model.Quote

interface QuoteScreenState {
    data class Presenting(
        val quoteEntry: Quote
    ): QuoteScreenState
    data class History(
        val historyQuotes: List<Quote>
    ): QuoteScreenState
}