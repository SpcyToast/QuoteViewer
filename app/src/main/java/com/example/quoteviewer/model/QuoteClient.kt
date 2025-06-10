package com.example.quoteviewer.model


import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.call.body
import io.ktor.client.request.header
import javax.inject.Inject


class QuoteClient @Inject constructor(){

    private val httpClient = HttpClient(OkHttp) {
        install(Logging)
        install(ContentNegotiation){
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun getQuote(): Quote{
        try {
            Log.i("Response","Called")
            val response = httpClient.get(HttpRoutes.QUOTE)
            {
                header("X-API-KEY", HttpRoutes.X_API_KEY)
            }
            Log.i("Response",response.body<List<Quote>>().first().quote)
            return response.body<List<Quote>>().first()
        } catch (e: Exception) {
            return Quote(e.message.toString(), "Error Message", "")
        }
    }
}