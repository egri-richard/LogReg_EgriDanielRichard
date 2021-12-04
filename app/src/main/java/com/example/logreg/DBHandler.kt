package com.example.logreg

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Toast
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

val DATABASE_NAME = "examDB.db"
val TABLE_NAME = "users"
val COL_ID = "id"
val COL_USERNAME = "username"
val COL_PASSWORD = "password"
val COL_IVPARAM = "ivparam"
val COL_EMAIL = "email"
val COL_FULLNAME = "fullname"

class DBHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable =   "CREATE TABLE $TABLE_NAME (" +
                            "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$COL_FULLNAME VARCHAR(256), " +
                            "$COL_USERNAME VARCHAR(256), " +
                            "$COL_PASSWORD BLOB, " +
                            "$COL_IVPARAM BLOB, " +
                            "$COL_EMAIL VARCHAR(256))"
        p0?.execSQL(createTable)

        val keyGenerator: KeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder("key",
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    private fun getKey(): SecretKey {
        val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        val secretKeyEntry: KeyStore.SecretKeyEntry = keyStore.getEntry("key", null) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey
    }

    fun encrypt(data: String): Pair<ByteArray, ByteArray> {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/NoPadding")

        var temp = data
        while (temp.toByteArray().size % 16 != 0) {
            temp += "\u0020"
        }

        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        val ivBytes = cipher.iv
        val encryptedBytes = cipher.doFinal(temp.toByteArray(Charsets.UTF_8))

        return Pair(ivBytes, encryptedBytes)
    }

    fun decrypt(ivBytes: ByteArray, data: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val ivParameterSpec = IvParameterSpec(ivBytes)

        cipher.init(Cipher.DECRYPT_MODE, getKey(), ivParameterSpec)
        return cipher.doFinal(data).toString(Charsets.UTF_8).trim()
    }

    fun insert(u: User, password: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_FULLNAME, u.teljnev)
        cv.put(COL_USERNAME, u.felhnev)

        val secretPass = encrypt(password)
        cv.put(COL_IVPARAM, secretPass.first)
        cv.put(COL_PASSWORD, secretPass.second)

        cv.put(COL_EMAIL, u.email)

        val result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Sikertelen feltoltes", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Sikeres feltoltes", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUsers(): MutableList<User> {
        val list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val u = User(   result.getString(result.getColumnIndex(COL_EMAIL)),
                                result.getString(result.getColumnIndex(COL_USERNAME)),
                                result.getBlob(result.getColumnIndex(COL_PASSWORD)),
                                result.getBlob(result.getColumnIndex(COL_IVPARAM)),
                                result.getString(result.getColumnIndex(COL_FULLNAME)))
                list.add(u)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun containsUsername(username: String): Boolean {
        var retBool = false
        val list = this.getUsers()

        for (user in list) {
            if (user.felhnev.equals(username)) {
                retBool = true
            }
        }
        return retBool
    }

    fun containsEmail(email: String): Boolean {
        var retBool = false
        val list = this.getUsers()

        for (user in list) {
            if (user.email.equals(email)) {
                retBool = true
            }
        }
        return retBool
    }
}