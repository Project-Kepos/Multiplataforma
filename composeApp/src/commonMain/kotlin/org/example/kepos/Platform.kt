package org.example.kepos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform