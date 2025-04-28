package com.example.myfaith.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myfaith.entity.Quote

class QuoteViewModel : ViewModel() {

    private val quotes = mutableListOf(
        Quote(
            "Есть две награды, которые многие не ценят. Это – здоровье и свободное время.",
            "Бухари",
            false
        ),
        Quote(
            "В Судный день каждым будет с тем, кого любил.",
            "Бухари",
            false
        ),
        Quote(
            "Игнорирование мирского (незаинтересованность) – успокаивает сердце и тело и д...",
            "Муснади-Шихаб",
            false
        ),
        Quote(
            "Поистине, Аллах не смотрит на ваш внешний вид и ваше имущество, но Он смотрит на ваши сердца и ваши дела.",
            "Муслим",
            false
        ),
        Quote(
            "Тот, кто верит в Аллаха и в Последний день, пусть говорит благое или молчит.",
            "Бухари и Муслим",
            false
        )
    )

    private var currentQuoteIndex = 0

    fun getCurrentQuote(): Quote? {
        return if (quotes.isNotEmpty()) quotes[currentQuoteIndex] else null
    }

    fun moveToNextQuote() {
        if (quotes.isNotEmpty()) {
            currentQuoteIndex = (currentQuoteIndex + 1) % quotes.size
        }
    }

    fun toggleFavorite(quote: Quote): Boolean {
        val index = quotes.indexOf(quote)
        if (index != -1) {
            quotes[index] = quote.copy(isFavorite = !quote.isFavorite)
            return quotes[index].isFavorite
        }
        return false
    }

    fun getFavoriteQuotes(): List<Quote> {
        return quotes.filter { it.isFavorite }
    }

    fun setCurrentQuoteIndex(favoriteIndex: Int) {
        val favoriteQuotes = getFavoriteQuotes()
        if (favoriteIndex < favoriteQuotes.size) {
            val quote = favoriteQuotes[favoriteIndex]
            currentQuoteIndex = quotes.indexOf(quote)
        }
    }
}
