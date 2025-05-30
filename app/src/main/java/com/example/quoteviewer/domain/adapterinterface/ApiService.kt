package com.example.quoteviewer.domain.adapterinterface

//https://medium.com/@humzakhalid94/api-integration-with-jetpack-compose-hilt-viewmodel-retrofit-a-comprehensive-guide-54ef905191bc
interface ApiService {
    @GET("quotes")
    suspend fun getQuotes(): List<Quote>
}