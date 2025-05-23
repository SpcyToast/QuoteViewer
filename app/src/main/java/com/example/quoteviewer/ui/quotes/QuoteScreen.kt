package com.example.quoteviewer.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quoteviewer.domain.Quote
import com.example.quoteviewer.ui.quotes.QuoteScreenState
import com.example.quoteviewer.ui.quotes.QuoteScreenVM

@Composable
fun QuoteScreen(
    viewModel: QuoteScreenVM = hiltViewModel()
) {

    val screenState by viewModel.stateFlow.collectAsState()

    QuoteView(
        screenState = screenState,
        newQuote = viewModel::newQuote,
        viewHistory = viewModel::viewHistory,
        focusQuote = viewModel::focusQuote
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuoteView(
    screenState: QuoteScreenState,
    newQuote: () -> Unit,
    viewHistory: () -> Unit,
    focusQuote: (Quote) -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Quotes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            modifier = Modifier.fillMaxHeight().width(200.dp),
                            shape = RectangleShape,
                            border = BorderStroke(2.dp, Color(0,0,0)),
                            onClick =
                                newQuote
                        ) {
                            Text(text = "New Quote")
                        }
//                        Spacer(modifier = Modifier.)
                        Button(
                            modifier = Modifier.fillMaxSize(),
                            shape = RectangleShape,
                            border = BorderStroke(2.dp, Color(0,0,0)),
                            onClick =
                                viewHistory
                        ) {
                            Text(text = "History")
                        }
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (screenState) {
                is QuoteScreenState.Presenting -> PresentingView(
                    screenState = screenState,
                    )
                is QuoteScreenState.History -> HistoryView(
                    screenState = screenState,
                    focusQuote = focusQuote,
                    )
            }
        }
    }
}


        @Composable
        private fun BoxScope.PresentingView(
            screenState: QuoteScreenState.Presenting,
        ) {
            Column {
                val author: String = screenState.quoteEntry.author
                Text(text = screenState.quoteEntry.quote)
                Text(text = "- $author")
            }
        }

        @Composable
        private fun BoxScope.HistoryView(
            screenState: QuoteScreenState.History,
            focusQuote: (Quote) -> Unit
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                screenState.history.forEach{ quote -> (

                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape,
                        border = BorderStroke(1.dp, Color(194,197,204)),
                        onClick = {
                            focusQuote(quote)
                        },
                    ) {
                        val thisAuthor = quote.author
                        Column(
                            modifier = Modifier.padding(horizontal = 0.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 0.dp),
                                text = quote.quote,
                                textAlign = TextAlign.Left,
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 0.dp),
                                text = "- $thisAuthor",
                                textAlign = TextAlign.Left
                            )
                        }
                    }
                        )}
            }
        }

////////////////////////////////////// Preview //////////////////////////////////////
        @Preview(showBackground = true, heightDp = 640)
        @Composable
        private fun PreviewPresenting() {
            QuoteViewerTheme {
                QuoteView(
                    screenState = QuoteScreenState.Presenting(
                        quoteEntry = Quote("Preview Quote", "Preview Author")
                    ),
                    newQuote = {},
                    viewHistory = {},
                    focusQuote = {}
                )
            }
        }

        @Preview(showBackground = true, heightDp = 640)
        @Composable
        private fun PreviewHistory() {
            QuoteViewerTheme {
                QuoteView(
                    screenState = QuoteScreenState.History(
                        history = listOf(
                            Quote("Preview Quote", "Preview Author"),
                            Quote("Preview Quote 2", "Preview Author 2"),
                            Quote("Preview Quote 3", "Preview Author 3"),
                            Quote("Preview Quote 4", "Preview Author 4")
                        )
                    ),
                    newQuote = {},
                    viewHistory = {},
                    focusQuote = {}
                )
            }
        }

