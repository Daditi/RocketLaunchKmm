package com.example.pleasework

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform