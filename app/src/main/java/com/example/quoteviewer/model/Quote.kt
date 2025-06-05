package com.example.quoteviewer.model

import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    val quote: String,
    val author: String,
    val category: String
)