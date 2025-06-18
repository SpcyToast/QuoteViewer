package com.example.quoteviewer.viewmodel

import com.example.quoteviewer.model.Quote
import com.example.quoteviewer.model.QuoteClient
import javax.inject.Inject

class QuoteSelector @Inject constructor(
    private val quoteClient: QuoteClient
){
    suspend fun fetchQuote(): Result<Quote> {
        try {
            val result:Quote = quoteClient.getQuote()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
