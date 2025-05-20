package com.example.quoteviewer.ui.quotes

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteviewer.domain.Quote
import com.example.quoteviewer.domain.QuoteSelecter
import com.example.quoteviewer.domain.QuotesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class QuoteScreenVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val quoteList: QuotesData
): ViewModel(){


    private val _stateFlow: MutableStateFlow<QuoteScreenState> =
        MutableStateFlow(QuoteScreenState.Presenting(Quote("","")))
    val stateFlow: StateFlow<QuoteScreenState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val index = LocalDate.now().toEpochDay().toInt() % QuotesData.dailyQuotes.size
            val todaysQuote: Quote = QuoteSelecter.getDailyQuote(index)
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = todaysQuote
                )
            )
            Log.v("trpb67", "emitResult is $emitResult")
        }
    }

    fun newQuote() = viewModelScope.launch {
//         call random number generator which uses QuotesData.dailyQuotes.size - 1
        val randomIndex: Int = 0
        val todaysQuote: Quote = QuoteSelecter.getDailyQuote(randomIndex)
        val emitResult = _stateFlow.tryEmit(
            QuoteScreenState.Presenting(
                quoteEntry = todaysQuote
            )
        )
        Log.v("trpb67", "emitResult is $emitResult")
    }

    fun viewHistory() = viewModelScope.launch {
        // display a list of old quotes in order of appearance
        val quoteHistory: List<Quote> = listOf(Quote("Sample Quote", "Sample Author"), Quote("Blink", "Blonk"))
        val emitResult = _stateFlow.tryEmit(
            QuoteScreenState.History(
                history = quoteHistory
            )
        )
        Log.v("trpb67", "emitResult is $emitResult")
    }

    fun focusQuote(selectedQuote: Quote) = viewModelScope.launch {
        // display a quote from history in the main view
        val emitResult = _stateFlow.tryEmit(
            QuoteScreenState.Presenting(
                quoteEntry = selectedQuote
            )
        )
        Log.v("trpb67", "emitResult is $emitResult")
    }
}

