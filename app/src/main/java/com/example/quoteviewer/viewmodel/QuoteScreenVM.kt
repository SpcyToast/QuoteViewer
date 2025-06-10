package com.example.quoteviewer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteviewer.viewmodel.QuoteScreenState
import com.example.quoteviewer.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteScreenVM @Inject constructor(
    private val quoteSelector: QuoteSelector
): ViewModel(){
    private val _stateFlow: MutableStateFlow<QuoteScreenState> =
        MutableStateFlow(QuoteScreenState.Presenting(Quote("", "", "")))
    val stateFlow: StateFlow<QuoteScreenState> = _stateFlow.asStateFlow()
    private val quoteHistory: MutableList<Quote> = mutableListOf()

    init {
        viewModelScope.launch {
            quoteSelector.fetchQuote().onSuccess { initalQuote ->
                quoteHistory.add(0, initalQuote)
                val emitResult = _stateFlow.tryEmit(
                    QuoteScreenState.Presenting(
                        quoteEntry = initalQuote
                    )
                )
            }.onFailure {  }
        }
    }

    fun newQuote() = viewModelScope.launch {
        quoteSelector.fetchQuote().onSuccess { nextQuote ->
            quoteHistory.add(0, nextQuote)
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = nextQuote
                )
            )
        }.onFailure {  }
    }

    fun viewHistory() = viewModelScope.launch {
        val emitResult = _stateFlow.tryEmit(
            QuoteScreenState.History(
                historyQuotes = quoteHistory
            )
        )
    }
}