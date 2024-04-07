package com.example.quizapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
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
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.EdgeDirection
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
        val fetchButton = findViewById<Button>(R.id.logout)

        fetchButton.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                // Redirect the user to the login screen or any other desired action after logout
            } else {
                Toast.makeText(this, "No user is currently logged in", Toast.LENGTH_SHORT).show()
            }
        }


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
//        val userIdTextView = findViewById<TextView>(R.id.textViewSignupTime)

        val firstLetterTextView = findViewById<TextView>(R.id.textView6)




        // Display user data in TextViews if user object is not null
        user?.let {
            nameTextView.text = "${it.name}"
            ageTextView.text = " ${it.age}"
            genderTextView.text = " ${it.gender}"

            val firstLetterOfTimeCreated = it.email.substring(0, 1).toUpperCase()
            firstLetterTextView.text = firstLetterOfTimeCreated

//            signupTimeTextView.text = " ${it.signupTime}"
            // Parse signupTime string to get date, month, and year
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val signUpDate = dateFormat.parse(it.signupTime)
            val formattedDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(signUpDate)

            signupTimeTextView.text = formattedDate
            emailTextView.text = "${it.email}"


            // Display only the first letter of the email address
            val firstLetter = it.email.substring(0, 1).toUpperCase()
            firstLetterTextView.text = firstLetter




        }
    }

}