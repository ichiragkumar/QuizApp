//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Spinner
//import android.widget.Toast
//import com.example.quizapp.R
//import com.example.quizapp.User
//import com.google.firebase.database.FirebaseDatabase
//import java.text.SimpleDateFormat
//import java.util.*
//
//class ProfileScreen : AppCompatActivity() {
//
//    private lateinit var editTextName: EditText
//    private lateinit var editTextAge: EditText
//    private lateinit var spinnerGender: Spinner
//    private lateinit var buttonSignUp: Button
//
//    private val database = FirebaseDatabase.getInstance("https://quizapp-7b14c-default-rtdb.asia-southeast1.firebasedatabase.app/")
//    private val usersRef = database.getReference("users")
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_profile_screen)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        editTextName = findViewById(R.id.editTextName)
//        editTextAge = findViewById(R.id.editTextAge)
//        spinnerGender = findViewById(R.id.spinnerGender)
//        buttonSignUp = findViewById(R.id.buttonSignUp)
//
//        buttonSignUp.setOnClickListener {
//            signUpUser()
//        }
//    }
//
//    private fun signUpUser() {
//        val name = editTextName.text.toString().trim()
//        val ageString = editTextAge.text.toString().trim()
//        val gender = spinnerGender.selectedItem.toString()
//
//        if (name.isEmpty() || ageString.isEmpty()) {
//            Toast.makeText(this, "Name and Age are required", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val age = ageString.toInt()
//        if (age < 0 || age > 150) {
//            Toast.makeText(this, "Invalid Age", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val currentTime = getCurrentTime()
//
//        val user = User(name, age, gender, currentTime)
//        saveUserToDatabase(user)
//    }
//
//    private fun getCurrentTime(): String {
//        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        return sdf.format(Date())
//    }
//
//    private fun saveUserToDatabase(user: User) {
//        val userId = usersRef.push().key
//        if (userId != null) {
//            usersRef.child(userId).setValue(user)
//            Toast.makeText(this, "User signed up successfully", Toast.LENGTH_SHORT).show()
//            finish()
//        } else {
//            Toast.makeText(this, "Failed to sign up user", Toast.LENGTH_SHORT).show()
//        }
//    }
//}
//
////data class User(
////    val name: String,
////    val age: Int,
////    val gender: String,
////    val signupTime: String
////)
