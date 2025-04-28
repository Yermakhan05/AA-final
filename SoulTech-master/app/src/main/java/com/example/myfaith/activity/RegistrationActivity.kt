package com.example.myfaith.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfaith.datasource.ApiSource
import com.example.myfaith.entity.response.RegistrationResponse
import com.example.mynavigationapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val emailField = findViewById<EditText>(R.id.registration_email)
        val numberField = findViewById<EditText>(R.id.registration_number)
        val nameField = findViewById<EditText>(R.id.registration_name)
        val usernameField = findViewById<EditText>(R.id.registration_username)
        val passwordField = findViewById<EditText>(R.id.registration_password)
        val registerButton = findViewById<Button>(R.id.registration_button)
        val loginButton = findViewById<TextView>(R.id.sign_in)
        val signInTextView = findViewById<TextView>(R.id.sign_in)

        val fullText = "Already have an account? Sign in"
        val spannable = SpannableString(fullText)
        val signInStart = fullText.indexOf("Sign in")

        if (signInStart != -1) {
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#f2f2ec")),
                0,
                signInStart,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#f4ff9d")),
                signInStart,
                fullText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            spannable.setSpan(ForegroundColorSpan(Color.parseColor("#f2f2ec")), 0, fullText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        signInTextView.text = spannable

        registerButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val number = numberField.text.toString().trim()
            val fullName = nameField.text.toString().trim()
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || number.isEmpty() || fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            ApiSource.registration.registerUser(
                email,
                username,
                password,
                fullName,
                number)
                .enqueue(object : Callback<RegistrationResponse> {
                    override fun onResponse(
                        call: Call<RegistrationResponse>,
                        response: Response<RegistrationResponse>
                    ) {
                        if (response.body() != null) {
//                            val token = response.body()!!.token
//                            val prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
//                            prefs.edit().putString("auth_token", token).apply()
                            val user = response.body()!!

                            Toast.makeText(this@RegistrationActivity, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                            intent.putExtra("email", user.email)
                            intent.putExtra("password", password)
                            startActivity(intent)
                            finish()

                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.e("Registration", "Error: ${response.code()} - $errorBody")
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Registration failed: ${errorBody ?: "Unknown error"}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                        Log.e("Registration", "Network error", t)
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Network error: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }


        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}