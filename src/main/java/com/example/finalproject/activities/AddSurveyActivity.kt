package com.example.finalproject.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.cotrollers.Functions
import com.example.finalproject.model.Survey
import java.text.SimpleDateFormat
import java.util.*


class AddSurveyActivity: AppCompatActivity() {

    private lateinit var myFun: Functions
    private var adminID = 0
    private lateinit var calendar: Calendar
    private lateinit var datePicker: DatePickerDialog
    private lateinit var surveyStartDate: EditText
    private lateinit var surveyEndDate: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_survey)

        adminID = intent.getIntExtra("adminID",0)

        myFun = Functions(this)

        surveyStartDate = findViewById(R.id.start_date)
        surveyStartDate.showSoftInputOnFocus = false
        surveyEndDate = findViewById(R.id.end_date)
        surveyEndDate.showSoftInputOnFocus = false

        calendar = Calendar.getInstance()
        datePicker = DatePickerDialog(this, { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel(calendar)
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))

        datePicker.datePicker.minDate = (System.currentTimeMillis() - 1000)
    }

    fun dateOnClick(view:View){
        datePicker.show()
    }

    private fun updateLabel(calendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val dateFormatter = SimpleDateFormat(myFormat, Locale.UK)
        if(surveyStartDate.hasFocus()){
            surveyStartDate.setText(dateFormatter.format(calendar.time))

        }else{
            surveyEndDate.setText(dateFormatter.format(calendar.time))
        }
    }

    fun btnPrevious(view: View){
        val intent = Intent(this, AdminSurveyActivity::class.java)
        startActivity(intent)
    }

    fun btnAddSurveyAction(view: View){

        val departmentID = myFun.getDepartmentID(adminID)
        val surveyTitle = findViewById<TextView>(R.id.survey_title).text.toString().uppercase()
        val surveyStartDate = findViewById<EditText>(R.id.start_date).text.toString()
        val surveyEndDate = findViewById<EditText>(R.id.end_date).text.toString()

        if (departmentID != 0 && surveyTitle.isNotEmpty() && surveyStartDate.isNotEmpty() && surveyEndDate.isNotEmpty()) {
            if (isValidSurveyTitle(surveyTitle)){
                val survey = Survey(0, departmentID, surveyTitle, surveyStartDate, surveyEndDate, false)
                if (myFun.addSurvey(survey)) {
                    Toast.makeText(this, "Survey added", Toast.LENGTH_SHORT).show()
                    finish()
                    val intent = Intent(this, AdminSurveyActivity::class.java)
                    intent.putExtra("adminID", 0)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error: Survey Already Exists", Toast.LENGTH_SHORT).show()
                }
            }else Toast.makeText(this, "Error: Title Must Have 4 letters and 4 digits", Toast.LENGTH_SHORT).show()

        }else {
            Toast.makeText(this, "Must fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidSurveyTitle(surveyTitle: String): Boolean {
        val titleRegex = """^[a-zA-Z]{4}\d{4}$"""
        return surveyTitle.matches(titleRegex.toRegex())
    }
}

