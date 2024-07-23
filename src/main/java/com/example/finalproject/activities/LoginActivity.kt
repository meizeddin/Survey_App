package com.example.finalproject.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.example.finalproject.R
import com.example.finalproject.cotrollers.Functions
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity(){

    private lateinit var myFun: Functions
    private lateinit var studentOrTeacher:SwitchCompat
    private lateinit var userEmail:EditText
    private lateinit var userPassword:EditText
    private var studentID: Int = 0
    private var adminID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage)
        myFun = Functions(this)
        studentOrTeacher = findViewById(R.id.swStudentTeacherL)
        userEmail = findViewById(R.id.txtEmailL)
        userPassword = findViewById(R.id.txtPasswordL)
    }

    fun btnFindUser(view: View) {
        if (studentOrTeacher.isChecked) {
            if (myFun.checkAdmin(userEmail.text.toString().lowercase(), userPassword.text.toString())) {
                Toast.makeText(this, "Admin Found", Toast.LENGTH_LONG).show()
                adminID = myFun.getAdminID(userEmail.text.toString().lowercase())
                val intent = Intent(this, AdminSurveyActivity::class.java)
                intent.putExtra("adminID", adminID)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            } else {
                Toast.makeText(this, "Error: Admin Not Found", Toast.LENGTH_LONG).show()
            }
        }else{
            if (myFun.checkStudent(userEmail.text.toString().lowercase(), userPassword.text.toString())) {
                Toast.makeText(this, "Student Found", Toast.LENGTH_LONG).show()
                val intent = Intent(this, StudentSurveyActivity::class.java)
                studentID = myFun.getStudentID(userEmail.text.toString().lowercase())
                intent.putExtra("studentId", studentID)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            } else {
                Snackbar.make(view, "Error: Student Not Found", Snackbar.LENGTH_SHORT).show()

            }
        }
    }

    fun btnRegisterL(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }
}