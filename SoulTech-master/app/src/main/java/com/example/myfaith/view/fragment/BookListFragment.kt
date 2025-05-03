package com.example.myfaith.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myfaith.R
import com.example.myfaith.view.adapter.BooksAdapter
import com.example.myfaith.model.entity.sampleBooks
import com.google.android.material.bottomnavigation.BottomNavigationView

class BookListFragment : Fragment() {

    private lateinit var booksRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        booksRecyclerView = view.findViewById(R.id.booksRecyclerView)
        booksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        booksRecyclerView.adapter = BooksAdapter(sampleBooks) { bookId ->
            val action = BooksFragmentDirections.actionBooksFragmentToBookDetailFragment(bookId)
            findNavController().navigate(action)
        }
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE
    }
}
