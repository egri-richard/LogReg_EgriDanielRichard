package com.example.logreg

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "examDB.db"
val TABLE_NAME = "users"
val COL_ID = "id"
val COL_USERNAME = "username"
val COL_PASSWORD = "password"
val COL_EMAIL = "email"
val COL_FULLNAME = "fullname"

class DBHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable =   "CREATE TABLE $TABLE_NAME (" +
                            "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$COL_FULLNAME VARCHAR(256), " +
                            "$COL_USERNAME VARCHAR(256), " +
                            "$COL_PASSWORD VARCHAR(256), " +
                            "$COL_EMAIL VARCHAR(256))"
        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insert(u: User) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_FULLNAME, u.teljnev)
        cv.put(COL_USERNAME, u.felhnev)
        cv.put(COL_PASSWORD, u.jelszo)
        cv.put(COL_EMAIL, u.email)

        val result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Sikertelen feltoltes", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Sikeres feltoltes", Toast.LENGTH_SHORT).show()
        }
    }
}