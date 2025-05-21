package com.example.quoteviewer.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    ){
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            when (screenState) {
                is QuoteScreenState.Presenting -> PresentingView(
                    screenState = screenState,
                    newQuote = newQuote,
                    viewHistory = viewHistory
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
    newQuote: () -> Unit,
    viewHistory: () -> Unit
){

        Text(text= screenState.quoteEntry.quote)
        Text(text= screenState.quoteEntry.author)
        Button(onClick = {
            newQuote()
        }) {
            Text(text="New Quote")
        }
        Button(onClick = {
            viewHistory()
        }) {
            Text(text="View History")
        }
}

@Composable
private fun BoxScope.HistoryView(
    screenState: QuoteScreenState.History,
    focusQuote: (Quote) -> Unit
){
    Column {
        screenState.history.forEach { quote ->
            Button(onClick = {
                focusQuote(quote)
            }) {
                Text(text=quote.quote)
                Text(text=quote.author)
            }
        }
    }
}

