package com.example.logreg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    private lateinit var logInText: EditText
    private lateinit var logInPass: EditText
    private lateinit var logInBtn: Button
    private lateinit var regBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        regBtn.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            finish()
        }

        logInBtn.setOnClickListener {
            val db = DBHandler(this)
            if (logInText.text.isNotEmpty() && logInPass.text.isNotEmpty()) {
                if (checkLogin() is User) {
                    val intent = Intent(applicationContext, LoggedInActivity::class.java)
                    intent.putExtra("fullName", checkLogin()?.teljnev.toString())
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "This user doesn't exist", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Please fill in every field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLogin() : User? {
        var retval: User? = null
        val db = DBHandler(this)
        val users = db.getUsers()

        for (user in users) {
            if (logInText.text.toString().equals(user.felhnev) && logInPass.text.toString().equals(user.jelszo)) {
                retval = user
            }
        }

        return retval
    }

    private fun init() {
        logInText = findViewById(R.id.logInText)
        logInPass = findViewById(R.id.logInPass)
        logInBtn = findViewById(R.id.logInBtn)
        regBtn = findViewById(R.id.regBtn)
    }
}