package com.example.logreg

class User {
    var id: Int = 0
    var email: String = ""
    var felhnev: String = ""
    var jelszo: String = ""
    var teljnev: String = ""

    constructor(id: Int, email: String, felhnev: String, jelszo: String, teljnev: String) {
        this.id = id
        this.email = email
        this.felhnev = felhnev
        this.jelszo = jelszo
        this.teljnev = teljnev
    }

    constructor(email: String, felhnev: String, jelszo: String, teljnev: String) {
        this.email = email
        this.felhnev = felhnev
        this.jelszo = jelszo
        this.teljnev = teljnev
    }

}