package com.example.finalproject.activities

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.finalproject.R
import com.example.finalproject.adapters.CustomAdapterQuestionsSpinners
import com.example.finalproject.adapters.CustomAdapterSurveySpinners
import com.example.finalproject.cotrollers.Functions
import com.example.finalproject.model.Answers
import com.example.finalproject.model.Questions
import com.example.finalproject.model.StudentResponse
import com.example.finalproject.model.Survey
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class PieChartStatsFragment: Fragment() {
    private lateinit var myFun: Functions
    private lateinit var spinnerSurvey: Spinner
    private lateinit var spinnerQuestions: Spinner
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
        val view = inflater.inflate(R.layout.activity_admin_fragment_piestats, container, false)

        myFun = Functions((activity as AdminSurveyActivity))
        spinnerSurvey = view.findViewById(R.id.spinner_survey_pieChartStats)
        spinnerQuestions = view.findViewById(R.id.spinner_question_pieChartStats)
        surveys = myFun.getFilteredSurveysList(3) //needs changing just trying
        val adapter = CustomAdapterSurveySpinners((activity as AdminSurveyActivity).applicationContext, surveys)
        spinnerSurvey.adapter = adapter
        spinnerSurvey.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                survey = surveys[position]
                questions = myFun.getAllQuestions(survey.id)
                val adapter = CustomAdapterQuestionsSpinners((activity as AdminSurveyActivity).applicationContext, questions)
                spinnerQuestions.adapter = adapter
                spinnerQuestions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        // Create a bar chart
                        val pieChart = (activity as AdminSurveyActivity).findViewById<PieChart>(R.id.pie_chart)
                        val color = ContextCompat.getColor((activity as AdminSurveyActivity),
                            R.color.md_theme_light_background
                        )
                        pieChart.setBackgroundColor(color)

                        question = questions[position]

                        // Set data for the Pie chart
                        val entries = ArrayList<PieEntry>()
                        val arrayX: ArrayList<String> = ArrayList()
                        var counter = 1f
                        var average = 0f
                        var count = 0
                        if(survey.id != 0 && question.id !=0) {
                            pieChart.clear()
                            answers = myFun.getAllAnswers()
                            for (a in answers) {
                                arrayX.add(a.answer)
                                responses =
                                    myFun.getAllResponsesByAnswer(survey.id, question.id, a.id)
                                if (responses.size != 0) {
                                    count = responses.size

                                    average =
                                        ((count * 100) / (myFun.numOfPraticipants(survey.id)) + 0f)
                                }
                                entries.add(PieEntry(average, ""+counter))

                                count = 0
                                average = 0f
                                counter++
                            }

                            val dataSet = PieDataSet(entries, "Surveys")
                            dataSet.valueTextColor = Color.BLACK
                            dataSet.setColors(ColorTemplate.JOYFUL_COLORS, 233)
                            dataSet.valueTextSize = 10f


                            val data = PieData(dataSet)
                            pieChart.data = data
                            pieChart.description.text = "PieChart"
                            pieChart.centerText = "Answer %"
                            pieChart.animateY(1000)
                            pieChart.invalidate() // refresh the chart
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

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