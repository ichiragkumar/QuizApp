package com.example.quizapp


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class ProfilePage : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var buttonSignUp: Button
    private lateinit var imageButtonProfilePhoto: ImageButton

    private val database = FirebaseDatabase.getInstance("https://quizapp-7b14c-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val usersRef = database.getReference("users")
    private val auth = FirebaseAuth.getInstance()

    // Request code for image picker
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fetchButton = findViewById<Button>(R.id.logout)
        val currentUser = FirebaseAuth.getInstance().currentUser

        val userId = currentUser?.uid ?: ""

        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        spinnerGender = findViewById(R.id.spinnerGender)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        imageButtonProfilePhoto = findViewById(R.id.imageButtonProfilePhoto)

        val spinnerGenderForItem: Spinner = findViewById(R.id.spinnerGender)
        val genderOptions = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGenderForItem.adapter = adapter

        buttonSignUp.setOnClickListener {
            signUpUser()
        }

        imageButtonProfilePhoto.setOnClickListener {
            openFileChooser()
        }

        // Check if the user is logged in and fill the email and userId fields

        if (currentUser != null) {
            val email = currentUser.email ?: ""
            val userId = currentUser.uid ?: ""
            // You can use the email and userId to pre-fill the corresponding fields
            // in the user's profile page or use them to create the User object
        }
    }

    // Function to open file chooser
    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun signUpUser() {
        val name = editTextName.text.toString().trim()
        val ageString = editTextAge.text.toString().trim()
        val gender = spinnerGender.selectedItem.toString()

        // Validate inputs
        if (name.isEmpty() || ageString.isEmpty()) {
            Toast.makeText(this, "Name and Age are required", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageString.toIntOrNull()
        if (age == null || age !in 0..150) {
            Toast.makeText(this, "Invalid Age", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select a profile photo", Toast.LENGTH_SHORT).show()
            return
        }

        val currentTime = getCurrentTime()

        // Create a User object with email and userId
        val currentUser = auth.currentUser
        val email = currentUser?.email ?: ""
        val userId = currentUser?.uid ?: ""
        val user = User(name, age, gender, currentTime, selectedImageUri.toString(), email, userId)

        // Save user to the database
        saveUserToDatabase(user)
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun saveUserToDatabase(user: User) {
        val userId = usersRef.push().key
        if (userId != null) {
            usersRef.child(userId).setValue(user)
            Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            // Load the selected image into ImageView if needed
            Glide.with(this)
                .load(selectedImageUri)
                .into(imageButtonProfilePhoto)
        }
    }





}
