package com.example.myfaith.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myfaith.BookListScreen
import com.example.myfaith.view.theme.BookReaderTheme

class BooksFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                BookReaderTheme {
                    BookListScreen(onBookClick = { bookId ->
                        val action = BooksFragmentDirections.actionBooksFragmentToBookDetailFragment(bookId)
                        findNavController().navigate(action)
                    })
                }
            }
        }
    }
}
