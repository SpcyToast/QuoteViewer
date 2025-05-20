package com.example.quoteviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.quoteviewer.ui.theme.QuoteScreen
import com.example.quoteviewer.ui.theme.QuoteViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteViewerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QuoteScreen()
                }
            }
        }
    }

}