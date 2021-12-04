package com.example.logreg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoggedInActivity : AppCompatActivity() {
    private lateinit var fullNameText : TextView
    private lateinit var logOutbtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
        init()
        val name = intent.getStringExtra("fullName")

        fullNameText.text = name

        logOutbtn.setOnClickListener {
            fullNameText.text = ""
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

    }

    private fun init() {
        fullNameText = findViewById(R.id.fullNameText)
        logOutbtn = findViewById(R.id.logOutBtn)
    }
}