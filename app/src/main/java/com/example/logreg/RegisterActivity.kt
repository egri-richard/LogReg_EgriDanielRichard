package com.example.logreg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    private lateinit var regEmailET : EditText
    private lateinit var regFelhnevET : EditText
    private lateinit var regPassET : EditText
    private lateinit var regFullNameET : EditText
    private lateinit var regBtn : Button
    private lateinit var backFromRegBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()

        backFromRegBtn.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        regBtn.setOnClickListener {
            val db = DBHandler(this)

            val uName = regFelhnevET.text.toString()
            val Email = regEmailET.text.toString()

            val validUName = !db.containsUsername(uName)
            val validEmail = !db.containsEmail(Email)

            if (validUName && validEmail) {
                db.insert(User(
                    regFullNameET.text.toString(),
                    regFelhnevET.text.toString(),
                    regPassET.text.toString(),
                    regEmailET.text.toString()
                ))

                Toast.makeText(applicationContext, "User added to database", Toast.LENGTH_SHORT).show()
            } else {
                if (validUName) {
                    Toast.makeText(applicationContext, "This Email address is already in use", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "This Username is already in use", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun init() {
        regEmailET = findViewById(R.id.regEmailET)
        regFelhnevET = findViewById(R.id.regFelhnevET)
        regPassET = findViewById(R.id.regPassET)
        regFullNameET = findViewById(R.id.regFullNameET)
        regBtn = findViewById(R.id.regBtn)
        backFromRegBtn = findViewById(R.id.backFromRegBtn)
    }
}