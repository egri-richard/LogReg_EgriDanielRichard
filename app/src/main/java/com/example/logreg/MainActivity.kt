package com.example.logreg

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import java.util.prefs.PreferenceChangeEvent
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    private lateinit var logInText: EditText
    private lateinit var logInPass: EditText
    private lateinit var logInBtn: Button
    private lateinit var regBtn: Button
    private lateinit var rememberCB : CheckBox
    private val db = DBHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val sharedRemeber = sharedPreferences.getBoolean("remember", false)
        val sharedFullName = sharedPreferences.getString("fullName", "")
        if (sharedRemeber) {
            val intent = Intent(this, LoggedInActivity::class.java)
            intent.putExtra("fullName", sharedFullName)
            startActivity(intent)
        }

        regBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        logInBtn.setOnClickListener {
            val db = DBHandler(this)
            if (logInText.text.isNotEmpty() && logInPass.text.isNotEmpty()) {
                if (checkLogin().id > -1) {
                    val intent = Intent(this, LoggedInActivity::class.java)
                    intent.putExtra("fullName", checkLogin().teljnev)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "This user doesn't exist", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Please fill in every field", Toast.LENGTH_SHORT).show()
            }
        }

        rememberCB.setOnCheckedChangeListener { _, _ ->
            if (rememberCB.isChecked) {
                val s = checkLogin().teljnev
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("remember", true)
                editor.putString("fullName", s)
                editor.apply()
            }
        }
    }

    private fun checkLogin() : User {
        val retval: User = User()
        val users = db.getUsers()

        for (user in users) {
            val temp = db.decrypt(user.ivParameterSpec, user.jelszo)

            if ((logInText.text.toString().equals(user.felhnev) || logInText.text.toString().equals(user.email)) &&
                logInPass.text.toString().equals(temp)) {
                return user
            }
        }

        return retval
    }

    private fun init() {
        logInText = findViewById(R.id.logInText)
        logInPass = findViewById(R.id.logInPass)
        logInBtn = findViewById(R.id.logInBtn)
        regBtn = findViewById(R.id.regBtn)
        rememberCB = findViewById(R.id.rememberCB)
    }
}