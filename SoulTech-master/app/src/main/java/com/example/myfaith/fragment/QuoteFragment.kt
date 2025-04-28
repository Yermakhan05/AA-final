package com.example.myfaith.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myfaith.datasource.ApiSource
import com.example.myfaith.entity.response.QuoteResponse
import com.example.mynavigationapp.R
import kotlinx.coroutines.launch
import retrofit2.Response

class QuoteFragment : Fragment() {

    private lateinit var quoteTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quote_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quoteTextView = view.findViewById(R.id.quoteTextView)
        fetchQuote()
    }

    private fun fetchQuote() {
        lifecycleScope.launch {
            try {
                val response: Response<QuoteResponse> = ApiSource.quoteApi.getTodayQuote()
                if (response.isSuccessful) {
                    val quote = response.body()
                    quote?.let {
                        quoteTextView.text = "\"${it.text}\"\n\n- ${it.author}"
                    } ?: run {
                        quoteTextView.text = getString(R.string.no_quote_available)
                    }
                } else {
                    quoteTextView.text = getString(R.string.error)
                    Log.e("QuoteFragment", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                quoteTextView.text = getString(R.string.error)
                Log.e("QuoteFragment", "Exception: ${e.message}", e)
            }
        }
    }
}
