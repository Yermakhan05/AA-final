package com.example.myfaith.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myfaith.R
import com.example.myfaith.viewmodel.QuoteViewModel

class QuoteFragment : Fragment() {

    private lateinit var quoteViewModel: QuoteViewModel
    private lateinit var quoteTextView: TextView
    private lateinit var quoteSourceView: TextView
    private lateinit var favoriteButton: ImageButton
    private lateinit var nextQuoteButton: Button
    private lateinit var favoritesButton: Button
    private lateinit var backButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quote, container, false)

        quoteTextView = view.findViewById(R.id.quoteTextView)
        quoteSourceView = view.findViewById(R.id.quoteSourceView)
        favoriteButton = view.findViewById(R.id.favoriteButton)
        nextQuoteButton = view.findViewById(R.id.nextQuoteButton)
        favoritesButton = view.findViewById(R.id.favoritesButton)
        backButton = view.findViewById(R.id.backButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quoteViewModel = ViewModelProvider(requireActivity())[QuoteViewModel::class.java]

        // Подписываемся на загрузку данных
        quoteViewModel.quotes.observe(viewLifecycleOwner) {
            displayCurrentQuote()
        }

        // Favorite button
        favoriteButton.setOnClickListener {
            val currentQuote = quoteViewModel.getCurrentQuote()
            if (currentQuote != null) {
                val isFavorite = quoteViewModel.toggleFavorite(currentQuote)
                updateFavoriteButtonState(isFavorite)
            }
        }

        // Next Quote button
        favoritesButton.setOnClickListener {
            quoteViewModel.moveToNextQuote()
            displayCurrentQuote()
        }

        // Favorites list button
        nextQuoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_quoteFragment_to_favoritesFragment)
        }

        // Back button
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun displayCurrentQuote() {
        val currentQuote = quoteViewModel.getCurrentQuote()
        if (currentQuote != null) {
            quoteTextView.text = currentQuote.text
            quoteSourceView.text = currentQuote.source
            updateFavoriteButtonState(currentQuote.isFavorite)
        }
    }

    private fun updateFavoriteButtonState(isFavorite: Boolean) {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_outline)
        }
    }
}
