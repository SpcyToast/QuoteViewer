package com.example.quoteviewer.viewmodel

import android.os.Messenger
import com.example.quoteviewer.model.Quote

interface QuoteScreenState {
    data object Loading: QuoteScreenState
    data class Presenting(
        val quoteEntry: Quote
    ): QuoteScreenState
    data class History(
        val historyQuotes: List<Quote>
    ): QuoteScreenState
}