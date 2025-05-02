package com.example.myfaith.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myfaith.R
import com.example.myfaith.entity.sampleBooks

class BookDetailFragment : Fragment() {

    private val args: BookDetailFragmentArgs by navArgs()

    private lateinit var coverImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var downloadButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_detail, container, false)

        coverImageView = view.findViewById(R.id.coverImageView)
        titleTextView = view.findViewById(R.id.titleTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        downloadButton = view.findViewById(R.id.downloadButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId = args.bookId
        val book = sampleBooks.find { it.id == bookId }

        book?.let {
            coverImageView.load(it.coverUrl)
            titleTextView.text = it.title
            descriptionTextView.text = it.description

            downloadButton.setOnClickListener {
                // Здесь мы позже реализуем скачивание PDF
            }
        }
    }
}
