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

class UpdateSurveyActivity:AppCompatActivity() {

    private lateinit var myFun: Functions
    private lateinit var calendar: Calendar
    private lateinit var datePicker: DatePickerDialog
    private lateinit var surveyTitleText: EditText
    private lateinit var surveyStartDate: EditText
    private lateinit var surveyEndDate: EditText
    private lateinit var publish: CheckBox
    private var departmentID = 0
    private var surveyID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_update)
        myFun = Functions(this)

        surveyTitleText = findViewById(R.id.editText_Survey_Title)
        surveyStartDate = findViewById(R.id.editText_Survey_StartDate)
        surveyStartDate.showSoftInputOnFocus = false
        surveyEndDate = findViewById(R.id.editText_Survey_EndDate)
        surveyEndDate.showSoftInputOnFocus = false
        publish = findViewById(R.id.checkbox_Survey_publish)

        calendar = Calendar.getInstance()
        datePicker = DatePickerDialog(this, { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel1(calendar)
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
        datePicker.datePicker.minDate = (System.currentTimeMillis() - 1000)

        val survey1 = intent.getSerializableExtra("survey") as? Survey
        if(survey1?.id != 0){
            surveyID = survey1!!.id
            departmentID = survey1.departmentId
            surveyTitleText.setText(survey1.title)
            surveyStartDate.setText(survey1.startDate)
            surveyEndDate.setText(survey1.endDate)
            publish.isChecked = survey1.isPublished
        }
    }

    fun dateOnClick1(view: View){
        datePicker.show()
    }

    private fun updateLabel1(calendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val dateFormatter = SimpleDateFormat(myFormat, Locale.UK)
        if(surveyStartDate.hasFocus()){
            surveyStartDate.setText(dateFormatter.format(calendar.time))

        }else{
            surveyEndDate.setText(dateFormatter.format(calendar.time))
        }
    }

    fun btnPrevious1(view: View){
        finish()
    }

    fun btnUpdateSurveyAction(view: View){

        val surveyID = surveyID
        val departmentID = departmentID
        val surveyTitle = surveyTitleText.text.toString().uppercase()
        val surveyStartDate = surveyStartDate.text.toString()
        val surveyEndDate = surveyEndDate.text.toString()
        val published = publish.isChecked
        val hasQuestions = myFun.checkSurveyHasQuestions(surveyID)

        if (!published || hasQuestions) {
            if (departmentID != 0 && surveyTitle.isNotEmpty() && surveyStartDate.isNotEmpty() && surveyEndDate.isNotEmpty()) {
                if(isValidSurveyTitle(surveyTitle)) {
                    val survey = Survey(
                        surveyID,
                        departmentID,
                        surveyTitle,
                        surveyStartDate,
                        surveyEndDate,
                        published
                    )
                    if (myFun.updateSurvey(survey)) {
                        Toast.makeText(this, "Survey updated", Toast.LENGTH_SHORT).show()
                        finish()
                        val intent = Intent(this, AdminSurveyActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error: can't update survey", Toast.LENGTH_SHORT)
                            .show()
                    }
                }else Toast.makeText(this, "Error: Title must have 4 letters and 4 digits", Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this, "Must fill all fields", Toast.LENGTH_SHORT).show()
            }
        } else {
            if(departmentID != 0 && surveyTitle.isNotEmpty() && surveyStartDate.isNotEmpty() && surveyEndDate.isNotEmpty()){
                Toast.makeText(this, "You must add questions to publish", Toast.LENGTH_LONG).show()
                val survey = Survey(0,departmentID,surveyTitle,surveyStartDate,surveyEndDate,true)
                val intent = Intent(this, AddQuestionsActivity::class.java)
                intent.putExtra("survey", survey)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Must fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isValidSurveyTitle(surveyTitle: String): Boolean {
        val titleRegex = """^[a-zA-Z]{4}\d{4}$"""
        return surveyTitle.matches(titleRegex.toRegex())
    }
}