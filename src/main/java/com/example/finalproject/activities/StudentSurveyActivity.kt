package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.adapters.CustomAdapterAllSurveys
import com.example.finalproject.adapters.CustomAdapterPublishedSurveys
import com.example.finalproject.cotrollers.Functions
import com.example.finalproject.model.*

class StudentSurveyActivity: AppCompatActivity() {

    private lateinit var myFun: Functions
    private lateinit var inCompletedSurveys: ListView
    private lateinit var completedSurveys: ListView
    private lateinit var helloText: TextView
    private var surveysInC : ArrayList<Survey> = ArrayList()
    private var surveysC : ArrayList<Survey> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_surveys)
        myFun = Functions(this)
        val studentId = intent.getIntExtra("studentId",0)
        helloText = findViewById(R.id.txtSurveyLogin)
        helloText.text = getString(R.string.user_info, myFun.getStudentName(studentId))



        surveysInC = myFun.getInCompletedSurveys(studentId)
        surveysC = myFun.getCompletedSurveys(studentId)

        inCompletedSurveys = findViewById(R.id.lstViewPublishedSurveys)
        val customAdapter = CustomAdapterPublishedSurveys(applicationContext, surveysInC)
        inCompletedSurveys.adapter = customAdapter
        inCompletedSurveys.isClickable = true
        inCompletedSurveys.setOnItemClickListener { _, _, position, _ ->
            val survey = surveysInC[position]
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("surveyId", survey.id)
            intent.putExtra("studentId", studentId)
            startActivity(intent)
        }

        completedSurveys = findViewById(R.id.lstViewCompletedSurveys)
        val customAdapterC = CustomAdapterAllSurveys(applicationContext, surveysC)
        completedSurveys.adapter = customAdapterC
    }

    fun btnLogout(view: View) {
        this@StudentSurveyActivity.finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}