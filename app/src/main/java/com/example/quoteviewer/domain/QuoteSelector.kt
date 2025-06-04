package com.example.quoteviewer.domain

import com.example.quoteviewer.domain.adapterinterface.ApiService
import com.example.quoteviewer.domain.adapterinterface.RetrofitInstance
import com.example.quoteviewer.domain.model.Quote
import kotlin.Result

class QuoteSelector{
    val quoteApi = RetrofitInstance.getQuote().create(ApiService::class.java)

    suspend fun invoke(): Result<Quote> {
        return try {
            val result: Quote = quoteApi.getQuote()
            return Result.success(result)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}
