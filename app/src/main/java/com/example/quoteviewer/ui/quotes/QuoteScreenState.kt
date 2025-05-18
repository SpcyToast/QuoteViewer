package com.example.quoteviewer.ui.quotes

import com.example.quoteviewer.domain.Quotes

interface QuoteScreenState {
    data class Presenting(
        val quote: Quotes
    ): QuoteScreenState
}
