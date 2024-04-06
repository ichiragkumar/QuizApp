package com.example.quizapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import android.net.Uri
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts

class ProfilePage : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var buttonSignUp: Button

    private val database = FirebaseDatabase.getInstance("https://quizapp-7b14c-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val usersRef = database.getReference("users")

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

        val spinnerGenderForItem: Spinner = findViewById(R.id.spinnerGender)
        val genderOptions = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGenderForItem.adapter = adapter



        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        spinnerGender = findViewById(R.id.spinnerGender)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        val imageButtonProfilePhoto = findViewById<ImageButton>(R.id.imageButtonProfilePhoto)

        buttonSignUp.setOnClickListener {
            signUpUser()
        }
        imageButtonProfilePhoto.setOnClickListener {
            openFileChooser()
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

        if (name.isEmpty() || ageString.isEmpty()) {
            Toast.makeText(this, "Name and Age are required", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageString.toInt()
        if (age < 0 || age > 150) {
            Toast.makeText(this, "Invalid Age", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select a profile photo", Toast.LENGTH_SHORT).show()
            return
        }

        val currentTime = getCurrentTime()

        val user = User(name, age, gender, currentTime, selectedImageUri.toString())
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
            Toast.makeText(this, "Prifle Saved", Toast.LENGTH_SHORT).show()
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
        }
    }
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }


}

