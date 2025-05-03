package com.example.myfaith.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfaith.model.entity.Quote
import com.example.myfaith.model.datasource.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuoteViewModel : ViewModel() {

    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>> get() = _quotes

    private var currentQuoteIndex = 0

    init {
        fetchQuotesFromApi()
    }

    private fun fetchQuotesFromApi() {
        RetrofitClient.instance.getHadiths().enqueue(object : Callback<List<Quote>> {
            override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                if (response.isSuccessful && response.body() != null) {
                    _quotes.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getCurrentQuote(): Quote? {
        return _quotes.value?.getOrNull(currentQuoteIndex)
    }

    fun moveToNextQuote() {
        val quotesList = _quotes.value ?: return
        if (quotesList.isNotEmpty()) {
            currentQuoteIndex = (currentQuoteIndex + 1) % quotesList.size
        }
    }

    fun toggleFavorite(quote: Quote): Boolean {
        val quotesList = _quotes.value?.toMutableList() ?: return false
        val index = quotesList.indexOf(quote)
        if (index != -1) {
            quotesList[index] = quote.copy(isFavorite = !quote.isFavorite)
            _quotes.value = quotesList
            return quotesList[index].isFavorite
        }
        return false
    }

    fun getFavoriteQuotes(): List<Quote> {
        return _quotes.value?.filter { it.isFavorite } ?: emptyList()
    }

    fun setCurrentQuoteIndex(favoriteIndex: Int) {
        val favoriteQuotes = getFavoriteQuotes()
        if (favoriteIndex < favoriteQuotes.size) {
            val quote = favoriteQuotes[favoriteIndex]
            currentQuoteIndex = _quotes.value?.indexOf(quote) ?: 0
        }
    }
}
