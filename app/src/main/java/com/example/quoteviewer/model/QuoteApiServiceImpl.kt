package com.example.quoteviewer.model

import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*

class QuoteApiServiceImpl(
    private val client: HttpClient
): QuoteApiService{
    override suspend fun getQuote(): Quote {
        return try {
            client.get {
                header("X-Api-Key", HttpRoutes.X_API_kEY)
                url(HttpRoutes.QUOTE)
            }
        }catch(e: RedirectResponseException){
            // 3xx - response
            Log.e("API RESPONSE", e.response.status.description)
            Quote("","","")
        }catch(e: ClientRequestException){
            // 4xx - response
            Log.e("API RESPONSE", e.response.status.description)
            Quote("","","")
        }catch(e: ServerResponseException){
            // 5xx - response
            Log.e("API RESPONSE", e.response.status.description)
            Quote("","","")
        }catch(e: Exception){
            // 5xx - response
            Log.e("API RESPONSE", e.message.toString())
            Quote("","","")
        } as Quote
    }
}