package com.example.logreg

class User {
    var id: Int = 0
    var email: String = ""
    var felhnev: String = ""
    var jelszo: ByteArray = byteArrayOf()
    var ivParameterSpec: ByteArray = byteArrayOf()
    var teljnev: String = ""

    constructor(id: Int, email: String, felhnev: String, jelszo: ByteArray, ivParam : ByteArray, teljnev: String) {
        this.id = id
        this.email = email
        this.felhnev = felhnev
        this.jelszo = jelszo
        this.ivParameterSpec = ivParam
        this.teljnev = teljnev
    }
    constructor(email: String, felhnev: String, jelszo: ByteArray, ivParam: ByteArray, teljnev: String) {
        this.email = email
        this.felhnev = felhnev
        this.jelszo = jelszo
        this.ivParameterSpec = ivParam
        this.teljnev = teljnev
    }

    constructor(email: String, felhnev: String, teljnev: String) {
        this.email = email
        this.felhnev = felhnev
        this.teljnev = teljnev
    }

    constructor()

}