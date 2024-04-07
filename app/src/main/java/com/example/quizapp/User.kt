package com.example.quizapp

import android.net.Uri



data class User(
    val name: String = "",
    val age: Int = 0,
    val gender: String = "",
    val signupTime: String = "",
    val selectedImageUri: String = "",
    val email: String = "",
    val userId: String = ""
) {
    // No-argument constructor
    constructor() : this("", 0, "", "", "", "", "")
}
