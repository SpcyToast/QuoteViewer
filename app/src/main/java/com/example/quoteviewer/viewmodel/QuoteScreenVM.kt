package com.example.quoteviewer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteviewer.viewmodel.QuoteScreenState
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
                        isLoading = false
                    )
                )
            }.onFailure { e ->
                val randomIndex = Random.nextInt(0, QuotesData.dailyQuotes.size -1)
                val emitResult = _stateFlow.tryEmit(
                    QuoteScreenState.Presenting(
                        quoteEntry = QuotesData.dailyQuotes[randomIndex],
                        isLoading = false
                    )
                )
                Log.e("fetchError",e.message.toString())
            }
        }
    }

    fun newQuote() = viewModelScope.launch {
        _stateFlow.value = QuoteScreenState.Presenting(quoteEntry = quoteHistory.first(), isLoading = true)
        quoteSelector.fetchQuote().onSuccess { nextQuote ->
            quoteHistory.add(0, nextQuote)
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = nextQuote,
                    isLoading = false
                )
            )
        }.onFailure { e ->
            val randomIndex = Random.nextInt(0, QuotesData.dailyQuotes.size -1)
            val emitResult = _stateFlow.tryEmit(
                QuoteScreenState.Presenting(
                    quoteEntry = QuotesData.dailyQuotes[randomIndex],
                    isLoading = false
                )
            )
            Log.e("fetchError",e.message.toString())
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