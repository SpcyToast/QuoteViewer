package com.example.quoteviewer.domain.adapterinterface

import com.example.quoteviewer.domain.model.Quote
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("X-Api-key: F8wg5wSU0LPO8ICHvPqtaQ==c4XjZeHLvdyC4Mc0")
    @GET("quotes")
    suspend fun getQuote(): Quote
}