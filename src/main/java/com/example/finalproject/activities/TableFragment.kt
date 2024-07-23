package com.example.finalproject.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.finalproject.R
import com.example.finalproject.adapters.CustomAdapterSurveySpinners
import com.example.finalproject.cotrollers.Functions
import com.example.finalproject.model.Answers
import com.example.finalproject.model.Questions
import com.example.finalproject.model.StudentResponse
import com.example.finalproject.model.Survey


class TableFragment: Fragment() {

    private lateinit var myFun: Functions
    private lateinit var spinnerSurvey: Spinner
    private var surveys : ArrayList<Survey> = ArrayList()
    private var questions : ArrayList<Questions> = ArrayList()
    private var responses: ArrayList<StudentResponse> = ArrayList()
    private var answers: ArrayList<Answers> = ArrayList()
    private lateinit var question: Questions
    private lateinit var survey: Survey

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_admin_fragment_table, container, false)
        val tableLayout = view.findViewById<TableLayout>(R.id.table_layout)

        myFun = Functions((activity as AdminSurveyActivity))
        spinnerSurvey = view.findViewById(R.id.spinner_survey_table)
        surveys = myFun.getFilteredSurveysList(3) //needs changing just trying

        val adapter = CustomAdapterSurveySpinners((activity as AdminSurveyActivity).applicationContext, surveys)
        spinnerSurvey.adapter = adapter
        spinnerSurvey.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(tableLayout.childCount > 1){
                    tableLayout.removeViews(1,10)
                }
                survey = surveys[position]
                questions = myFun.getAllQuestions(survey.id)
                answers = myFun.getAllAnswers()
                var count = 0
                var average = 0f
                var counter = 1

                for(i in 0 until questions.size){
                    val tableRow = inflater.inflate(R.layout.activity_admin_fragment_table_row, tableLayout, false)
                    val questionN = tableRow.findViewById<TextView>(R.id.columnQN)
                    val answer1 = tableRow.findViewById<TextView>(R.id.columnA1)
                    answer1.text = "${0f}%"
                    val answer2 = tableRow.findViewById<TextView>(R.id.columnA2)
                    answer2.text = "${0f}%"
                    val answer3 = tableRow.findViewById<TextView>(R.id.columnA3)
                    answer3.text = "${0f}%"
                    val answer4 = tableRow.findViewById<TextView>(R.id.columnA4)
                    answer4.text = "${0f}%"
                    val answer5 = tableRow.findViewById<TextView>(R.id.columnA5)
                    answer5.text = "${0f}%"
                    questionN.text = "${i+1}"

                    question = questions[i]
                    for (a in answers) {
                        responses = myFun.getAllResponsesByAnswer(survey.id, question.id, a.id)
                        if (responses.size != 0) {
                            count = responses.size
                            average = ((count * 100) / (myFun.numOfPraticipants(survey.id)) + 0f)
                            when(counter){
                                1 -> answer1.text = "$average%"
                                2 -> answer2.text = "$average%"
                                3 -> answer3.text = "$average%"
                                4 -> answer4.text = "$average%"
                                5 -> answer5.text = "$average%"
                            }
                        }
                        counter++
                    }
                    counter = 1
                    val parent1 = tableRow.parent
                    if(parent1 != null){
                        (parent1 as ViewGroup).removeView(tableRow)
                    }
                    tableLayout.addView(tableRow)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        // Set up the listView of the fragment
        return view
    }
}