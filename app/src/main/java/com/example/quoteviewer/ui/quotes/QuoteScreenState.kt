package com.example.quoteviewer.ui.quotes

import com.example.quoteviewer.domain.Quote

interface QuoteScreenState {
    data class Presenting(
        val quote: Quote
    ): QuoteScreenState
}
