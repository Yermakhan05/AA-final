package com.example.myfaith.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.view.adapter.FavoritesAdapter
import com.example.myfaith.viewmodel.QuoteViewModel
import com.example.myfaith.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoritesFragment : Fragment() {

    private lateinit var quoteViewModel: QuoteViewModel
    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var emptyStateTextView: TextView
    private lateinit var favoritesCountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE

        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView)
        emptyStateTextView = view.findViewById(R.id.emptyStateTextView)
        favoritesCountTextView = view.findViewById(R.id.favoritesCountTextView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quoteViewModel = ViewModelProvider(requireActivity())[QuoteViewModel::class.java]

        // Set up RecyclerView
        favoritesAdapter = FavoritesAdapter(quoteViewModel.getFavoriteQuotes()) { position ->
            // Handle click on favorite item
            quoteViewModel.setCurrentQuoteIndex(position)
            findNavController().navigate(R.id.action_favoritesFragment_to_quoteFragment)
        }

        favoritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesAdapter
        }


        // Update favorites count
        updateFavoritesCount()

        // Show empty state if no favorites
        updateEmptyState()
    }

    override fun onResume() {
        super.onResume()
        // Refresh favorites list when returning to this fragment
        favoritesAdapter.updateData(quoteViewModel.getFavoriteQuotes())
        updateFavoritesCount()
        updateEmptyState()
    }

    private fun updateFavoritesCount() {
        val count = quoteViewModel.getFavoriteQuotes().size
        favoritesCountTextView.text = getString(R.string.favorite_hadiths_count, count)
    }

    private fun updateEmptyState() {
        if (quoteViewModel.getFavoriteQuotes().isEmpty()) {
            emptyStateTextView.visibility = View.VISIBLE
            favoritesRecyclerView.visibility = View.GONE
        } else {
            emptyStateTextView.visibility = View.GONE
            favoritesRecyclerView.visibility = View.VISIBLE
        }
    }
}
