package com.example.quizapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.User
import com.google.firebase.database.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditProfileScreen : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_screen)

        val gotoSignUpScreen = findViewById<Button>(R.id.editProfile)
        gotoSignUpScreen.setOnClickListener {
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
            finish()
        }

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance("https://quizapp-7b14c-default-rtdb.asia-southeast1.firebasedatabase.app/")
        // Reference to "users" node
        usersRef = database.getReference("users")

        // Fetch data from Firebase Database
        fetchUserData()

    }

    private fun fetchUserData() {

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        updateUI(user)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun updateUI(user: User?) {
        // Assuming you have TextViews in your layout with these IDs
        val nameTextView = findViewById<TextView>(R.id.textViewName)
        val ageTextView = findViewById<TextView>(R.id.textViewAge)
        val genderTextView = findViewById<TextView>(R.id.textViewGender)
        val signupTimeTextView = findViewById<TextView>(R.id.textViewEmail)
        val emailTextView = findViewById<TextView>(R.id.textViewUserId)
        val userIdTextView = findViewById<TextView>(R.id.textViewSignupTime)

        // Display user data in TextViews if user object is not null
        user?.let {
            nameTextView.text = "Name: ${it.name}"
            ageTextView.text = "Age: ${it.age}"
            genderTextView.text = "Gender: ${it.gender}"
            signupTimeTextView.text = "Signup Time: ${it.signupTime}"
            emailTextView.text = "Email: ${it.email}"
            userIdTextView.text = "User ID: ${it.userId}"
        }
    }

}