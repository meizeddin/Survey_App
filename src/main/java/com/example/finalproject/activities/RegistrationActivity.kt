package com.example.finalproject.activities
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.finalproject.R
import com.example.finalproject.adapters.CustomAdapterDepartment
import com.example.finalproject.cotrollers.Functions
import com.example.finalproject.model.Department
import com.example.finalproject.model.Admin
import com.example.finalproject.model.Student


class RegistrationActivity: AppCompatActivity() {

    private lateinit var myFun: Functions
    private lateinit var departmentMenu: AutoCompleteTextView
    private lateinit var departmentTxt: com.google.android.material.textfield.TextInputLayout
    private var departmentID = 0
    private var departments : ArrayList<Department> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerpage)
        myFun = Functions(this)
        departments = myFun.getDepartmentList() //Get all surveys
        departmentMenu = findViewById(R.id.EditText_Department)
        departmentMenu.showSoftInputOnFocus = false
        departmentTxt = findViewById(R.id.txtDepartment)

        val customAdapter = CustomAdapterDepartment(applicationContext, departments)
        departmentMenu.setAdapter(customAdapter)
        departmentMenu.setOnItemClickListener { parent, _, position, _ ->
            val department = parent.getItemAtPosition(position) as Department
            departmentID = department.id
        }
    }

    @SuppressLint("CutPasteId")
    fun btnRegisterAdmin(view: View) {
        val departmentId = departmentID
        val fullname = findViewById<EditText>(R.id.txtFullnameR).text.toString()
        val email = findViewById<EditText>(R.id.txtEmailR).text.toString().lowercase()
        val adminKey =  findViewById<EditText>(R.id.txtAdminKeyR).text.toString()
        val password = findViewById<EditText>(R.id.txtPassR).text.toString()
        val adminOrStudent = findViewById<CheckBox>(R.id.isTeacherR).isChecked

        if (adminOrStudent){
            if (validateRegistrationInputAdmin(departmentId, fullname, email, adminKey,password)) {
                if(validateAdminKey(adminKey)) {
                    if(isValidEmail(email)) {
                        if (isValidPassword(password)){
                            val admin = Admin(0, departmentId, fullname, email, adminKey, password)
                            if (myFun.addAdmin(admin)) {
                                Toast.makeText(
                                    this,
                                    "An Admin Has Been Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findViewById<EditText>(R.id.txtFullnameR).text.clear()
                                findViewById<EditText>(R.id.txtEmailR).text.clear()
                                findViewById<EditText>(R.id.txtAdminKeyR).text.clear()
                                findViewById<EditText>(R.id.txtPassR).text.clear()
                                findViewById<CheckBox>(R.id.isTeacherR).isChecked = false
                            } else Toast.makeText(
                                this,
                                "Error: Admin Already Exists",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else Toast.makeText(this, "Error: Password must at least have 1 letter and 3 digits", Toast.LENGTH_LONG).show()
                    }else Toast.makeText(this, "Error: Enter a proper email", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Error: Key doesn't match our records", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Error: Fill All Required Fields", Toast.LENGTH_SHORT).show()
            }
        }else{
            if (validateRegistrationInput(departmentId, fullname, email, password)) {
                if(isValidEmail(email)) {
                    if(isValidPassword(password)){
                        val student = Student(0, departmentId, fullname, email, password)
                        if (myFun.addStudent(student)) {
                            Toast.makeText(
                                this,
                                "A Student Has Been Added Successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            findViewById<EditText>(R.id.txtFullnameR).text.clear()
                            findViewById<EditText>(R.id.txtEmailR).text.clear()
                            findViewById<EditText>(R.id.txtAdminKeyR).text.clear()
                            findViewById<EditText>(R.id.txtPassR).text.clear()
                            findViewById<CheckBox>(R.id.isTeacherR).isChecked = false
                        } else Toast.makeText(
                            this,
                            "Error: Student Already Exists",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else Toast.makeText(this, "Error: Password must at least have 1 letter and 3 digits", Toast.LENGTH_LONG).show()
                }else Toast.makeText(this, "Error: Enter a proper email", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Error: Fill All Required Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun btnLogin(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)

    }

    fun validateRegistrationInput(
        departmentID: Int,
        fullname: String,
        email: String,
        password: String
    ): Boolean{
        if(fullname.isEmpty() || email.isEmpty() || password.isEmpty() || departmentID == 0) {
            return false
        }
        return true
    }

    private fun validateRegistrationInputAdmin(
        departmentID: Int,
        fullname: String,
        email: String,
        key: String,
        password: String
    ): Boolean{
        if(fullname.isEmpty() || email.isEmpty() || key.isEmpty() || password.isEmpty() || departmentID == 0) {
            return false
        }
        return true
    }

    private fun validateAdminKey(
        key: String
    ): Boolean{
        if(key != "Admin123"){
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$"""
        return email.matches(emailRegex.toRegex())
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordRegex = """^(?=.*[A-Za-z])(?=.*\d{3})[A-Za-z\d]{4,}$"""
        return password.matches(passwordRegex.toRegex())
    }
}