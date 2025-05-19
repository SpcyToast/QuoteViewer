package com.example.quoteviewer.domain

object QuoteSelecter {
    fun getDailyQuote(index: Int): Quote {
        try {
            return QuotesData.dailyQuotes[index]
        } catch (e: Exception) {
            println("invalid quote index")
            return Quote("When things fail, the best thing to do is learn from it and keep moving forward", "Prashniel Kumar")
        }
    }
}