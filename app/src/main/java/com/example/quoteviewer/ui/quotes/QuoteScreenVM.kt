package com.example.quoteviewer.ui.quotes

import android.util.Log
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
import kotlin.random.Random

@HiltViewModel
class QuoteScreenVM @Inject constructor(): ViewModel(){


    private val _stateFlow: MutableStateFlow<QuoteScreenState> =
        MutableStateFlow(QuoteScreenState.Presenting(Quote("","")))
    val stateFlow: StateFlow<QuoteScreenState> = _stateFlow.asStateFlow()
    val quoteHistory: MutableList<Quote> = mutableListOf()

    init {
        viewModelScope.launch {
            val index = LocalDate.now().toEpochDay().toInt() % QuotesData.dailyQuotes.size
            val todaysQuote: Quote = QuoteSelecter.getDailyQuote(index)
            quoteHistory.add(0, todaysQuote)
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = todaysQuote
                )
            )
            Log.v("trpb67", "emitResult is $emitResult")
        }
    }

    fun newQuote() = viewModelScope.launch {
        val randomIndex: Int = Random.nextInt(0, QuotesData.dailyQuotes.size -1)
        val nextQuote: Quote = QuoteSelecter.getDailyQuote(randomIndex)
        quoteHistory.add(0, nextQuote)
        val emitResult = _stateFlow.tryEmit(
            QuoteScreenState.Presenting(
                quoteEntry = nextQuote
            )
        )
        Log.v("trpb67", "emitResult is $emitResult")
    }

    fun viewHistory() = viewModelScope.launch {
        val emitResult = _stateFlow.tryEmit(
            QuoteScreenState.History(
                history = quoteHistory
            )
        )
        Log.v("trpb67", "emitResult is $emitResult")
    }

    fun focusQuote(selectedQuote: Quote) = viewModelScope.launch {
        val emitResult = _stateFlow.tryEmit(
            QuoteScreenState.Presenting(
                quoteEntry = selectedQuote
            )
        )
        Log.v("trpb67", "emitResult is $emitResult")
    }
}

