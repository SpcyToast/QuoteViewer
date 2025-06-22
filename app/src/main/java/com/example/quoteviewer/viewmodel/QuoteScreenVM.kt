package com.example.quoteviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        MutableStateFlow(QuoteScreenState.Loading)
    val stateFlow: StateFlow<QuoteScreenState> = _stateFlow.asStateFlow()
    private val quoteHistory: MutableList<Quote> = mutableListOf()

    init {
        newQuote()
    }

    fun newQuote() = viewModelScope.launch {
        _stateFlow.value = QuoteScreenState.Loading
        quoteSelector.fetchQuote().onSuccess { nextQuote ->
            quoteHistory.add(0, nextQuote)
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = nextQuote,
                )
            )
        }.onFailure { e ->
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Error(
                    previousQuote = quoteHistory.firstOrNull(),
                    errorMessage = e.message.toString()
                )
            )
        }
    }

    fun viewHistory() {
        _stateFlow.tryEmit(
            QuoteScreenState.History(
                historyQuotes = quoteHistory
            )
        )
    }
}
