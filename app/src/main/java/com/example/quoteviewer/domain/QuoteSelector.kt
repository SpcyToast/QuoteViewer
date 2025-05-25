package com.example.quoteviewer.domain

import java.time.LocalDate
import kotlin.random.Random

object QuoteSelector {

    fun getDailyQuote(): Quote {
        val index = todayQuoteIndex()
        return QuotesData.dailyQuotes[index]
    }

    fun getRandomQuote(): Quote {
        val randomIndex: Int = Random.nextInt(0, QuotesData.dailyQuotes.size -1)
        return QuotesData.dailyQuotes[randomIndex]
    }

    private fun todayQuoteIndex(): Int =
        LocalDate.now().toEpochDay().toInt() % QuotesData.dailyQuotes.size

}
