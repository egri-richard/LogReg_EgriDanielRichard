package com.example.logreg

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
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
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        fullNameText.text = name

        logOutbtn.setOnClickListener {
            fullNameText.text = ""

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("remember", false)
            editor.apply()

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

    }

    private fun init() {
        fullNameText = findViewById(R.id.fullNameText)
        logOutbtn = findViewById(R.id.logOutBtn)
    }
}
