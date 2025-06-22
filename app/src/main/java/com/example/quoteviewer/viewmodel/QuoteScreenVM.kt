package com.example.quoteviewer.viewmodel

import androidx.annotation.VisibleForTesting
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
        fetchNewQuote()
    }

    @VisibleForTesting
    suspend fun fetchNewQuote() {
        _stateFlow.value = QuoteScreenState.Loading
        val fetchQuoteResult = quoteSelector.fetchQuote()
        val nextQuote = fetchQuoteResult.getOrNull()
        if (nextQuote == null) {
            val error = fetchQuoteResult.exceptionOrNull()!!
            _stateFlow.emit(
                QuoteScreenState.Error(
                    previousQuote = quoteHistory.firstOrNull(),
                    errorMessage = error.message.toString()
                )
            )
        } else {
            quoteHistory.add(0, nextQuote)
            _stateFlow.emit(
                QuoteScreenState.Presenting(
                    quoteEntry = nextQuote,
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
