package com.example.quoteviewer.domain

object QuoteSelecter {
    fun getDailyQuote(index: Int): Quote {
        return QuotesData.dailyQuotes[index]
    }
}