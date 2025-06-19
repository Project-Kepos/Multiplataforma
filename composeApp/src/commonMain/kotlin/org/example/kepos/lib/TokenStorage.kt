package org.example.kepos.lib

import java.io.File

object TokenStorage {

    private val file = File(System.getProperty("user.home"), ".kepos-token.txt")

    fun saveToken(token: String) {
        file.writeText(token)
    }

    fun getToken(): String? {
        return if (file.exists()) file.readText() else null
    }

    fun removeToken() {
        if (file.exists()) file.delete()
    }
}
