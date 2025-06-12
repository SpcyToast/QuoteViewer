package com.example.quoteviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteviewer.model.Quote
import com.example.quoteviewer.model.QuotesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuoteScreenVM @Inject constructor(
    private val quoteSelector: QuoteSelector
): ViewModel(){
    private val _stateFlow: MutableStateFlow<QuoteScreenState> =
        MutableStateFlow(QuoteScreenState.Loading)
    val stateFlow: StateFlow<QuoteScreenState> = _stateFlow.asStateFlow()
    private val quoteHistory: MutableList<Quote> = mutableListOf()

    init {
        viewModelScope.launch {
            quoteSelector.fetchQuote().onSuccess { initalQuote ->
                quoteHistory.add(0, initalQuote)
                val emitResult = _stateFlow.tryEmit(
                    QuoteScreenState.Presenting(
                        quoteEntry = initalQuote,
                        errorMessage = null
                    )
                )
            }.onFailure { e ->
                val randomIndex = Random.nextInt(0, QuotesData.dailyQuotes.size -1)
                val initalQuote = QuotesData.dailyQuotes[randomIndex]
                quoteHistory.add(0, initalQuote)
                val emitResult = _stateFlow.tryEmit(
                    QuoteScreenState.Presenting(
                        quoteEntry = initalQuote,
                        errorMessage = e.message.toString()
                    )
                )
            }
        }
    }

    fun newQuote() = viewModelScope.launch {
        _stateFlow.value = QuoteScreenState.Loading
        quoteSelector.fetchQuote().onSuccess { nextQuote ->
            quoteHistory.add(0, nextQuote)
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = nextQuote,
                    errorMessage = null
                )
            )
        }.onFailure { e ->
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = quoteHistory.first(),
                    errorMessage = e.message.toString()
                )
            )
        }
    }

    fun viewHistory() = viewModelScope.launch {
        val emitResult = _stateFlow.tryEmit(
            QuoteScreenState.History(
                historyQuotes = quoteHistory
            )
        )
    }
}