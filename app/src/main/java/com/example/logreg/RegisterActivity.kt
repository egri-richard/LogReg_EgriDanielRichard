package com.example.logreg

import android.content.Intent
import android.graphics.Color
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
    private val db = DBHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()

        backFromRegBtn.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        regEmailET.setOnFocusChangeListener { view, b ->
            val text = regEmailET.text.toString()
            if (!regEmailET.isFocused) {
                if (db.containsEmail(text)) {
                    regEmailET.setTextColor(Color.parseColor("#FF0000"))
                }
            } else {
                regEmailET.setTextColor(Color.parseColor("#000000"))
            }
        }

        regFelhnevET.setOnFocusChangeListener { view, b ->
            val text = regFelhnevET.text.toString()
            if (!regFelhnevET.isFocused) {
                if (db.containsUsername(text)) {
                    regFelhnevET.setTextColor(Color.parseColor("#FF0000"))
                }
            } else {
                regFelhnevET.setTextColor(Color.parseColor("#000000"))
            }
        }

        regBtn.setOnClickListener {
            if (validation()) {
                db.insert(User(
                    regEmailET.text.toString(),
                    regFelhnevET.text.toString(),
                    regPassET.text.toString(),
                    regFullNameET.text.toString()
                ))
            }
        }
    }

    private fun validation() : Boolean {
        var retBool = true

        //email validation
        val emailText = regEmailET.text.toString()
        if (!emailText.contains(".")) {
            retBool = false
            Toast.makeText(applicationContext, "Wrong email format", Toast.LENGTH_SHORT).show()
            return retBool
        } else if(!emailText.contains("@")) {
            retBool = false
            Toast.makeText(applicationContext, "Wrong email format", Toast.LENGTH_SHORT).show()
            return retBool
        } else if (db.containsEmail(emailText)) {
            retBool = false
            Toast.makeText(applicationContext, "This email is already in use", Toast.LENGTH_SHORT).show()
            return retBool
        }

        //username validation
        val uName = regFelhnevET.text.toString()
        if (db.containsUsername(uName)) {
            retBool = false
            Toast.makeText(applicationContext, "This username already exists", Toast.LENGTH_SHORT).show()
            return retBool
        }

        //full name validation
        val fullName = regFullNameET.text.toString()
        val fnWords = fullName.split(" ").toMutableList()
        if(fnWords.count() > 1) {
            for (word in fnWords) {
                if (!word.first().isUpperCase()) {
                    retBool = false
                    Toast.makeText(applicationContext, "Your name must start with a capital letter", Toast.LENGTH_SHORT).show()
                    return retBool
                }
            }
        } else {
            retBool = false
            Toast.makeText(applicationContext, "Your must write in your full name", Toast.LENGTH_SHORT).show()
            return retBool
        }

        return retBool
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