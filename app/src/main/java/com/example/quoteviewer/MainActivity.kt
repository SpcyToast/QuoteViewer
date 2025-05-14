package com.example.quoteviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quoteviewer.theme.QuoteViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteViewerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QuoteViewer()
                }
            }
        }
    }

}

@Composable
fun QuoteViewer() {
    Text(text = "Placeholder")
}

@Preview(showBackground = true)
@Composable
fun SandboxPreview() {
    Surface {
        QuoteViewer()
    }
}