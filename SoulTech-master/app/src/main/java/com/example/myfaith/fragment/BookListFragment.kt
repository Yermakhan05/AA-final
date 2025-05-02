package com.example.myfaith.fragment

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
import com.example.myfaith.entity.sampleBooks

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
    }
}

class BooksAdapter(
    private val books: List<com.example.myfaith.entity.Book>,
    private val onBookClick: (Int) -> Unit
) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookImage: ImageView = view.findViewById(R.id.bookCoverImage)
        val bookTitle: TextView = view.findViewById(R.id.bookTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bookTitle.text = book.title
        holder.bookImage.load(book.coverUrl)
        holder.itemView.setOnClickListener {
            onBookClick(book.id)
        }
    }
}
