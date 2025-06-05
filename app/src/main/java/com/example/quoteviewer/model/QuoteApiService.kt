package com.example.quoteviewer.model

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import kotlinx.serialization.serializer

interface QuoteApiService {
    suspend fun getQuote(): Quote

    companion object {
        fun create(): QuoteApiService {
            return QuoteApiServiceImpl(
                client = HttpClient(Android){
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}