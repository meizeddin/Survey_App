package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.cotrollers.Functions
import com.example.finalproject.model.Answers
import com.example.finalproject.model.Questions
import com.example.finalproject.model.StudentResponse

class AnswerActivity : AppCompatActivity() {

    private lateinit var myFun: Functions
    private var answers : ArrayList<Answers> = ArrayList()
    private var questions: ArrayList<Questions> = ArrayList()
    private var responses: ArrayList<StudentResponse> = ArrayList()
    private var counter: Int = 0
    private var questionId: Int = 0
    private var answerId: Int = 0
    private var studentId: Int = 0
    private var surveyId: Int = 0
    private lateinit var question: TextView
    private lateinit var radioGroup: RadioGroup
    private var checkedRadioGroup: Int = 0
    private lateinit var checkedButtonString: String
    private lateinit var radioButton0: RadioButton
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton4: RadioButton
    private lateinit var viewStub: ViewStub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentquestion)

        myFun = Functions(this)

        answers = myFun.getAllAnswers()

        radioGroup = findViewById(R.id.radioGroup0)
        radioButton0 = findViewById(R.id.radioButton0)
        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)
        radioButton3 = findViewById(R.id.radioButton3)
        radioButton4 = findViewById(R.id.radioButton4)

        radioButton0.text = answers[4].answer
        radioButton1.text = answers[3].answer
        radioButton2.text = answers[2].answer
        radioButton3.text = answers[1].answer
        radioButton4.text = answers[0].answer

        viewStub = findViewById(R.id.questions_student_stub)
        viewStub.inflate()
        viewStub.visibility = View.INVISIBLE

        studentId = intent.getIntExtra("studentId", 0)
        surveyId = intent.getIntExtra("surveyId", 0)

        questions = myFun.getAllQuestions(surveyId)
        question = findViewById(R.id.question)

        if (questions.isNotEmpty()){
            question.text = questions[counter].toString()
        }

    }

    fun btnPrevious(view: View) {
        if (counter == 0){
            finish()
        }else if(counter < questions.size){
            responses.removeAt(counter - 1)
            counter--
            question.text = questions[counter].toString()
        }else {
            viewStub.visibility = View.INVISIBLE
            responses.removeAt(counter - 1)
            counter--
            question.text = questions[counter].toString()
        }
    }

    fun btnNext(view: View) {
        checkedRadioGroup = radioGroup.checkedRadioButtonId
        if (counter < questions.size) {
            if(radioGroup.checkedRadioButtonId != -1) {

                val checkedRadioButton = findViewById<RadioButton>(checkedRadioGroup)
                checkedButtonString = checkedRadioButton?.text.toString()

                questionId = myFun.getQuestionId(surveyId, question.text.toString())
                answerId = myFun.getAnswerId(checkedButtonString)
                val respond = StudentResponse(0, studentId, surveyId, questionId, answerId)
                responses.add(respond)
                //val gson = Gson()
                //val arrayString = gson.toJson(responses)
                //val values = ContentValues()
                //values.put("Array", arrayString)
                counter++
                radioGroup.clearCheck()
                if(counter < questions.size) {
                    question.text = questions[counter].toString()
                } else {
                    viewStub.visibility = View.VISIBLE
                }
            }else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_LONG).show()
            }
        }else {
            for(x in responses){
                myFun.addResponse(x)
            }
            Toast.makeText(this, "All Done", Toast.LENGTH_LONG).show()
            val intent = Intent(this, StudentSurveyActivity::class.java)
            intent.putExtra("studentId", studentId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }
    fun radioButtonWidget(view: View){}
}

