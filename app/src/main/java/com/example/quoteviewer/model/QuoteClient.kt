package com.example.quoteviewer.model


import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.http.headers
import io.ktor.client.call.body
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

    suspend fun getQuote(): Quote?{
        try {
            val response = httpClient.get(HttpRoutes.QUOTE)
            {
                headers {
                    append("X-API-KEY", HttpRoutes.X_API_kEY)
                }
            }
            return response.body<Quote>()
        } catch (e: Exception) {
            return Quote(e.message.toString(), "", "")
        }
    }
}