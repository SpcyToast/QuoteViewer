package com.example.quoteviewer.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun QuoteScreen() {
    Box(){
        Text(text="placeholder quote")
        Text(text="placeholder author")
        Button(onClick = {
//            onClickHandler()
        }) {
            Text(text="New Quote")
        }
    }
}