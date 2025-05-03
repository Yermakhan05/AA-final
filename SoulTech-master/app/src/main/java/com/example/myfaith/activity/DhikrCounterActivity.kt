package com.example.myfaith.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myfaith.R

class DhikrCounterActivity : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var arabicTextView: TextView
    private lateinit var translationTextView: TextView
    private lateinit var counterTextView: TextView
    private lateinit var resetButton: Button
    private var counter = 0
    private lateinit var vibrator: Vibrator

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dhikr_counter)

        nameTextView = findViewById(R.id.dhikrNameTextView)
        arabicTextView = findViewById(R.id.arabicTextView)
        translationTextView = findViewById(R.id.translationTextView)
        counterTextView = findViewById(R.id.counterTextView)
        resetButton = findViewById(R.id.resetButton)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Get dhikr details from intent
        val dhikrName = intent.getStringExtra("dhikr_name") ?: ""
        val dhikrArabic = intent.getStringExtra("dhikr_arabic") ?: ""
        val dhikrTranslation = intent.getStringExtra("dhikr_translation") ?: ""

        // Set the text views
        nameTextView.text = dhikrName
        arabicTextView.text = dhikrArabic
        translationTextView.text = dhikrTranslation
        updateCounter()

        // Set up click listeners
        counterTextView.setOnClickListener {
            counter++
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            updateCounter()
        }

        resetButton.setOnClickListener {
            counter = 0
            updateCounter()
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }

    private fun updateCounter() {
        counterTextView.text = counter.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt("counter", 0)
        updateCounter()
    }
} 