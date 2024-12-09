package com.example.project

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Activity for user login and registration
class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        // Handle login button click
        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            // Check if the provided username and password are correct
            if (username == "admin" && password == "password") {
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply() // Save login state

                val intent = Intent(this, MainActivity::class.java) // Redirect to MainActivity
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show() // Show error
            }
        }

        // Handle registration button click
        registerButton.setOnClickListener {
            Toast.makeText(this, "Registration not implemented", Toast.LENGTH_SHORT).show()
        }
    }
}
