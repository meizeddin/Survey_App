package com.example.finalproject.activities

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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class BarChartStatsFragment: Fragment() {
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
        val view = inflater.inflate(R.layout.activity_admin_fragment_stats, container, false)

        myFun = Functions((activity as AdminSurveyActivity))
        spinnerSurvey = view.findViewById(R.id.spinner_survey_barChartStats)
        spinnerQuestions = view.findViewById(R.id.spinner_question_barChartStats)
        surveys = myFun.getFilteredSurveysList(3) //needs changing just trying
        surveys.add(0,Survey(0, 0,"All Surveys","","",false))
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
                questions.add(0,Questions(0, 0,"All Questions"))
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
                        val barChart = (activity as AdminSurveyActivity).findViewById<BarChart>(R.id.bar_chart)
                        val color = ContextCompat.getColor((activity as AdminSurveyActivity),
                            R.color.md_theme_light_background
                        )
                        barChart.setBackgroundColor(color)
                        barChart.setBorderWidth(2f)
                        barChart.setDrawBorders(true)
                        barChart.setFitBars(true)
                        barChart.description.text="Bar Chart"
                        barChart.animateY(1000)
                        question = questions[position]

                        // Set data for the bar chart
                        val entries = ArrayList<BarEntry>()
                        val arrayX: ArrayList<String> = ArrayList()
                        var counter = 0f
                        var average = 0f
                        var count = 0
                        val xAxis = barChart.xAxis
                        val yAxis = barChart.axisLeft
                        val rightAxis = barChart.axisRight
                        rightAxis.isEnabled = false
                        xAxis.granularity = 1f

                        if(survey.id == 0){
                            for(x in surveys){
                                if(x.id != 0) {
                                    responses = myFun.getAllResponsesBySurvey(x.id)
                                    arrayX.add(x.title)

                                    if(responses.size != 0) {
                                        for (r in responses) {
                                            count += r.answerID
                                        }
                                        average = (count / (responses.size + 0f))
                                    }
                                    entries.add(BarEntry(counter, average))
                                    count = 0
                                    average = 0f
                                    counter++
                                }
                            }

                            val dataSet = BarDataSet(entries, "Surveys")
                            val color = ContextCompat.getColor((activity as AdminSurveyActivity),
                                R.color.md_theme_dark_secondaryContainer
                            )
                            dataSet.color = color
                            xAxis.valueFormatter = IndexAxisValueFormatter(arrayX)
                            xAxis.labelRotationAngle = 0f
                            xAxis.position = XAxis.XAxisPosition.TOP
                            yAxis.axisMinimum = 0f
                            yAxis.axisMaximum = 5.9f

                            val data = BarData(dataSet)
                            barChart.data = data
                            barChart.invalidate() // refresh the chart

                        }else if(question.id == 0){
                            barChart.clear()
                            for(q in questions){
                                arrayX.add("Q${counter}")
                                responses = myFun.getAllResponsesByQuestion(survey.id, q.id)


                                if(responses.size != 0) {
                                    for (r in responses) {
                                        count += r.answerID
                                    }
                                    average = (count / (myFun.numOfPraticipants(survey.id))+0f)
                                }
                                entries.add(BarEntry(counter, average))
                                count = 0
                                average = 0f
                                counter++
                            }

                            val dataSet = BarDataSet(entries, "Surveys")
                            val color = ContextCompat.getColor((activity as AdminSurveyActivity),
                                R.color.md_theme_light_tertiary
                            )
                            dataSet.color = color
                            xAxis.valueFormatter = IndexAxisValueFormatter(arrayX)
                            xAxis.granularity = 1f
                            xAxis.labelRotationAngle = 0f
                            xAxis.position = XAxis.XAxisPosition.TOP
                            yAxis.axisMinimum = 0f
                            yAxis.axisMaximum = 5.9f

                            val data = BarData(dataSet)
                            barChart.data = data
                            barChart.invalidate() // refresh the chart
                        }else{
                            barChart.clear()
                            answers = myFun.getAllAnswers()
                            for(a in answers){
                                arrayX.add(a.answer)
                                responses = myFun.getAllResponsesByAnswer(survey.id, question.id, a.id)
                                if(responses.size != 0) {
                                    count = responses.size

                                    average = ((count *100) / (myFun.numOfPraticipants(survey.id))+0f)
                                }
                                entries.add(BarEntry(counter, average))
                                count = 0
                                average = 0f
                                counter++
                            }

                            val dataSet = BarDataSet(entries, "Surveys")
                            val color = ContextCompat.getColor((activity as AdminSurveyActivity),
                                R.color.md_theme_dark_secondary
                            )
                            dataSet.color = color
                            xAxis.valueFormatter = IndexAxisValueFormatter(arrayX)
                            xAxis.granularity = 1f
                            xAxis.labelRotationAngle = 270f
                            xAxis.position = XAxis.XAxisPosition.TOP
                            yAxis.axisMinimum = 0f
                            yAxis.axisMaximum = 100.9f

                            val data = BarData(dataSet)
                            barChart.data = data
                            barChart.invalidate() // refresh the chart
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