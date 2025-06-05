package com.example.quoteviewer.viewmodel

import androidx.compose.runtime.produceState
import com.example.quoteviewer.model.QuotesData
import com.example.quoteviewer.model.Quote
import com.example.quoteviewer.model.QuoteApiService
import io.ktor.util.valuesOf
import java.time.LocalDate
import kotlin.random.Random

class QuoteSelector {

    private val service = QuoteApiService.create()

    val quote = produceState<Quote>(
        initialValue = Quote("","",""),
        producer = {
            value = service.getQuote()
        }
    ) { }

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
