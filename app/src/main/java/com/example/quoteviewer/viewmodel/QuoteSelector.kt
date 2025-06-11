package com.example.quoteviewer.viewmodel

import android.util.Log
import com.example.quoteviewer.model.Quote
import com.example.quoteviewer.model.QuoteClient
import javax.inject.Inject

class QuoteSelector @Inject constructor(
    private val quoteClient: QuoteClient
){
    suspend fun fetchQuote(): Result<Quote> {
        return try {
            val result:Quote = quoteClient.getQuote()
            Result.success(result)
        } catch (e: Exception) {
            Log.e("Error",e.message.toString())
            Result.failure(e)
        }
    }
}
