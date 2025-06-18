package com.example.quoteviewer.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quoteviewer.viewmodel.QuoteScreenState
import com.example.quoteviewer.viewmodel.QuoteScreenVM
import com.example.quoteviewer.model.Quote
import com.example.quoteviewer.view.theme.QuoteViewerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun QuoteScreen(
    viewModel: QuoteScreenVM = hiltViewModel()
) {
    val screenState by viewModel.stateFlow.collectAsState()

    QuoteView(
        screenState = screenState,
        viewNewQuote = viewModel::newQuote,
        viewHistory = viewModel::viewHistory,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuoteView(
    screenState: QuoteScreenState,
    viewNewQuote: () -> Unit,
    viewHistory: () -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
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
                    TextButton(
                        onClick = viewNewQuote,
                        modifier = Modifier.semantics { testTag="new_quote" }
                    ) {
                        Text(text = "New Quote")
                    }

                    TextButton(
                        onClick = viewHistory,
                        modifier = Modifier.semantics { testTag="view_history" }
                    ) {
                        Text(text = "History")
                    }
                }
            )
        },
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (screenState) {
                is QuoteScreenState.Loading -> LoadingView()
                is QuoteScreenState.Presenting -> PresentingView(
                    screenState = screenState,
                    snackbarHostState = snackbarHostState,
                    scope = scope,
                    viewNewQuote = viewNewQuote
                )

                is QuoteScreenState.History -> HistoryView(
                    screenState = screenState
                )
            }
        }
    }
}


@Composable
private fun ColumnScope.LoadingView() {
    Box(modifier = Modifier.height(20.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        text = "Loading....",
    )
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ColumnScope.PresentingView(
    screenState: QuoteScreenState.Presenting,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    viewNewQuote: () -> Unit
) {
    Box(modifier = Modifier.height(20.dp))
    SingleQuoteView(screenState.quoteEntry)
    if (screenState.errorMessage != null){
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = screenState.errorMessage,
                actionLabel = "retry",
                duration = SnackbarDuration.Indefinite,
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    viewNewQuote()
                }
                SnackbarResult.Dismissed -> {}
            }
        }
    }
}

@Composable
private fun ColumnScope.HistoryView(
    screenState: QuoteScreenState.History,
) {
    val quoteList = screenState.historyQuotes
    LazyColumn {
        items(items = quoteList) {
            SingleQuoteView(it)
            HorizontalDivider()
        }
    }
}

@Composable
private fun SingleQuoteView(quote: Quote?) {
    val quoteExists = quote != null
    val thisQuote = if (quoteExists) quote.quote else ""
    val thisAuthor = if (quoteExists) "- ${quote.author}" else ""
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        Text(
            text = thisQuote,
            modifier = Modifier.semantics {
                testTag="quote_content"
            }
        )
        Text(
            text = thisAuthor,
            modifier = Modifier.align(Alignment.End).semantics {
                testTag="quote_author"
            },
        )
    }
}

////////////////////////////////////// Preview //////////////////////////////////////
@Preview(showBackground = true, heightDp = 640)
@Composable
private fun PreviewLoading() {
    QuoteViewerTheme {
        QuoteView(
            screenState = QuoteScreenState.Loading,
            viewNewQuote = {},
            viewHistory = {},
        )
    }
}


@Preview(showBackground = true, heightDp = 640)
@Composable
private fun PreviewPresenting() {
    QuoteViewerTheme {
        QuoteView(
            screenState = QuoteScreenState.Presenting(
                quoteEntry = Quote(
                    "Follow your bliss and the universe will open doors where there are only walls.",
                    "Joseph Campbell",
                    ""
                ),
                errorMessage = ""
            ),
            viewNewQuote = {},
            viewHistory = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 640)
@Composable
private fun PreviewHistory() {
    QuoteViewerTheme {
        QuoteView(
            screenState = QuoteScreenState.History(
                historyQuotes = listOf(
                    Quote("Alone we can do so little; together we can do so much.", "Preview Author",""),
                    Quote("Nothing lasts forever. Not even your troubles.", "Preview Author 2",""),
                    Quote("The pessimist complains about the wind. The optimist expects it to change. The leader adjusts the sails.", "Preview Author 3",""),
                    Quote("Preview Quote 4", "Preview Author 4","")
                )
            ),
            viewNewQuote = {},
            viewHistory = {},
        )
    }
}

