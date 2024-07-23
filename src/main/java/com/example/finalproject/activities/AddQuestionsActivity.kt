package com.example.finalproject.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.cotrollers.Functions
import com.example.finalproject.model.Questions
import com.example.finalproject.model.Survey

class AddQuestionsActivity: AppCompatActivity() {

    private lateinit var myFun: Functions
    private var questions: ArrayList<Questions> = ArrayList()
    private lateinit var survey: Survey

    private lateinit var question1: EditText
    private lateinit var question2: EditText
    private lateinit var question3: EditText
    private lateinit var question4: EditText
    private lateinit var question5: EditText
    private lateinit var question6: EditText
    private lateinit var question7: EditText
    private lateinit var question8: EditText
    private lateinit var question9: EditText
    private lateinit var question10: EditText

    private lateinit var popupWindow: PopupWindow
    private lateinit var popUpBtnSubmit: Button
    private lateinit var popUpBtnPrevious: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_form)

        myFun = Functions(this)
        survey = intent.getSerializableExtra("survey") as Survey

        question1 = findViewById(R.id.EditText_add_question1)
        question2 = findViewById(R.id.EditText_add_question2)
        question3 = findViewById(R.id.EditText_add_question3)
        question4 = findViewById(R.id.EditText_add_question4)
        question5 = findViewById(R.id.EditText_add_question5)
        question6 = findViewById(R.id.EditText_add_question6)
        question7 = findViewById(R.id.EditText_add_question7)
        question8 = findViewById(R.id.EditText_add_question8)
        question9 = findViewById(R.id.EditText_add_question9)
        question10 = findViewById(R.id.EditText_add_question10)

        val layoutInflater = layoutInflater.inflate(R.layout.activity_admin_done_questions, null)
        popupWindow = PopupWindow(layoutInflater, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable = true
        popupWindow.contentView = layoutInflater
        popUpBtnPrevious = layoutInflater.findViewById(R.id.ViewStub_btnPrevious)
        popUpBtnSubmit = layoutInflater.findViewById(R.id.ViewStub_btnNext)

        if (myFun.checkSurveyHasQuestions(survey.id)){
            questions = myFun.getAllQuestions(survey.id)
            question1.setText(questions[0].question)
            question2.setText(questions[1].question)
            question3.setText(questions[2].question)
            question4.setText(questions[3].question)
            question5.setText(questions[4].question)
            question6.setText(questions[5].question)
            question7.setText(questions[6].question)
            question8.setText(questions[7].question)
            question9.setText(questions[8].question)
            question10.setText(questions[9].question)
        }

    }

    fun btnNext3(view: View){
        if(question1.text.isNotEmpty() && question2.text.isNotEmpty()
            &&question3.text.isNotEmpty() && question4.text.isNotEmpty()
            &&question5.text.isNotEmpty() && question6.text.isNotEmpty()
            &&question7.text.isNotEmpty() && question8.text.isNotEmpty()
            &&question9.text.isNotEmpty() && question10.text.isNotEmpty()){

            if(myFun.checkSurveyHasQuestions(survey.id)){

                questions[0].question = question1.text.toString()
                questions[1].question = question2.text.toString()
                questions[2].question = question3.text.toString()
                questions[3].question = question4.text.toString()
                questions[4].question = question5.text.toString()
                questions[5].question = question6.text.toString()
                questions[6].question = question7.text.toString()
                questions[7].question = question8.text.toString()
                questions[8].question = question9.text.toString()
                questions[9].question = question10.text.toString()
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                popUpBtnSubmit.setOnClickListener {
                    var check = false
                    for (q in questions) {
                        check = myFun.updateQuestion(q)
                    }
                    if (check) {
                        Toast.makeText(this, "Questions updated Successfully", Toast.LENGTH_SHORT)
                            .show()
                        popupWindow.dismiss()
                        finish()
                    } else {
                        Toast.makeText(this, "Error: Questions not updated", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                popUpBtnPrevious.setOnClickListener {
                    popupWindow.dismiss()
                }
            }else {
                questions.add(Questions(0,survey.id,question1.text.toString()))
                questions.add(Questions(0,survey.id,question2.text.toString()))
                questions.add(Questions(0,survey.id,question3.text.toString()))
                questions.add(Questions(0,survey.id,question4.text.toString()))
                questions.add(Questions(0,survey.id,question5.text.toString()))
                questions.add(Questions(0,survey.id,question6.text.toString()))
                questions.add(Questions(0,survey.id,question7.text.toString()))
                questions.add(Questions(0,survey.id,question8.text.toString()))
                questions.add(Questions(0,survey.id,question9.text.toString()))
                questions.add(Questions(0,survey.id,question10.text.toString()))
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                popUpBtnSubmit.setOnClickListener {
                    var check = false
                    for(q in questions){
                        check = myFun.addQuestion(q)
                    }
                    if(check) {
                        Toast.makeText(this, "Questions Added Successfully", Toast.LENGTH_SHORT).show()
                        popupWindow.dismiss()
                        finish()
                    }else {
                        Toast.makeText(this, "Error: Questions not added", Toast.LENGTH_SHORT).show()
                    }
                }
                popUpBtnPrevious.setOnClickListener {
                    popupWindow.dismiss()
                }
            }
        }else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    fun btnPrevious3(view: View){
        finish()
    }
}