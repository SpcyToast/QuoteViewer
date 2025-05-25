package com.example.quoteviewer.ui.quotes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteviewer.domain.Quote
import com.example.quoteviewer.domain.QuoteSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteScreenVM @Inject constructor(): ViewModel(){

    private val _stateFlow: MutableStateFlow<QuoteScreenState> =
        MutableStateFlow(QuoteScreenState.Presenting(Quote("","")))
    val stateFlow: StateFlow<QuoteScreenState> = _stateFlow.asStateFlow()
    private val quoteHistory: MutableList<Quote> = mutableListOf()

    init {
        viewModelScope.launch {
            val todayQuote: Quote = QuoteSelector.getDailyQuote()
            quoteHistory.add(0, todayQuote)
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = todayQuote
                )
            )
            Log.v("trpb67", "emitResult is $emitResult")
        }
    }

    fun newQuote() = viewModelScope.launch {
        val nextQuote: Quote = QuoteSelector.getRandomQuote()
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

