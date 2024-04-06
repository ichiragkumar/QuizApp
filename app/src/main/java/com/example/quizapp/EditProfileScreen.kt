package com.example.quizapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

class EditProfileScreen : AppCompatActivity() {

    private lateinit var editTextName: TextView
    private lateinit var editTextAge: TextView
    private lateinit var userGender: TextView
    private lateinit var signupTime: TextView
    private lateinit var userimageViewProfilePhoto: ImageView

    private val database = FirebaseDatabase.getInstance("https://quizapp-7b14c-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val usersRef = database.getReference("users")

    private var currentUserID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val goTOEditProfileScreen = findViewById<Button>(R.id.editProfile)
        goTOEditProfileScreen.setOnClickListener{
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
            finish()
        }



        editTextName = findViewById(R.id.textViewName)
        editTextAge = findViewById(R.id.textViewAge)
        userGender = findViewById(R.id.textViewGender)
        signupTime = findViewById(R.id.textViewSignupTime)
        userimageViewProfilePhoto = findViewById(R.id.imageViewProfilePhoto)

        // Get the current user's ID from the intent or wherever it's stored
        currentUserID = intent.getStringExtra("userID")

        // Fetch and display the current user's information
        fetchCurrentUser()

    }
    private fun fetchCurrentUser() {
        currentUserID?.let { uid ->
            usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        user?.let {
                            // Populate TextViews with user's information
                            editTextName.text = "Name: ${user.name}"
                            editTextAge.text = "Age: ${user.age}"
                            userGender.text = "Gender: ${user.gender}"
                            signupTime.text = "Account Created At: ${user.signupTime}"
                            Glide.with(this@EditProfileScreen)
                                .load(user.selectedImageUri)
                                .placeholder(R.drawable.user) // Placeholder image while loading
                                .error(com.google.android.material.R.drawable.mtrl_ic_error) // Error image if loading fails
                                .into(userimageViewProfilePhoto)

                            Toast.makeText(
                                this@EditProfileScreen,
                                "User information fetched successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Show message if user data doesn't exist
                        Toast.makeText(
                            this@EditProfileScreen,
                            "User data not found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled event
                    Toast.makeText(
                        this@EditProfileScreen,
                        "Failed to fetch user data: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }


}
