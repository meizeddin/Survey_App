package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.finalproject.R
import com.example.finalproject.activities.AdminSurveyActivity
import com.example.finalproject.adapters.CustomAdapterAllSurveys
import com.example.finalproject.cotrollers.Functions
import com.example.finalproject.model.Survey

class SurveyFragment: Fragment() {
    private lateinit var filteredSurveysList: ListView
    private var surveys : ArrayList<Survey> = ArrayList()
    private lateinit var myFun: Functions
    private lateinit var spinner: Spinner
    private lateinit var customAdapterListView: CustomAdapterAllSurveys
    private lateinit var popupWindow: PopupWindow
    private lateinit var btnBack: Button
    private lateinit var btnAddQuestions: Button
    private lateinit var btnUpdate: Button
    private lateinit var popupText: TextView
    private lateinit var survey: Survey


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myFun = Functions(activity as AdminSurveyActivity)
        val view = inflater.inflate(R.layout.activity_admin_fragment_surveys, container, false)

        // Set up the listView of the fragment

        filteredSurveysList = view.findViewById(R.id.listView_fragment)

        val layoutInflater = layoutInflater.inflate(R.layout.activity_admin_survey_popup, null)
        popupWindow = PopupWindow(layoutInflater, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable = true
        popupWindow.contentView = layoutInflater
        btnBack = layoutInflater.findViewById(R.id.btnBackPopUp)
        btnBack.isFocusable = false
        btnBack.setOnClickListener {
            popupWindow.dismiss()
        }
        btnAddQuestions = layoutInflater.findViewById(R.id.btnAddQuestionsPopUp)
        btnAddQuestions.isFocusable = false
        btnUpdate = layoutInflater.findViewById(R.id.btn_Update_PopUp)
        btnUpdate.isFocusable = false


        // Create an ArrayAdapter to bind the list of items to the spinner
        spinner = view.findViewById(R.id.spinner)
        val spinnerItems = arrayOf("UnPublished","Published","Expired")
        val adapter = ArrayAdapter((activity as AdminSurveyActivity), R.layout.activity_dropdown_menu_item, spinnerItems)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (parent?.getItemAtPosition(position) as String) {
                    "UnPublished" -> {
                        surveys = myFun.getFilteredSurveysList(1) //Get all surveys
                        customAdapterListView = CustomAdapterAllSurveys((activity as AdminSurveyActivity).applicationContext, surveys)
                    }
                    "Published" -> {
                        surveys = myFun.getFilteredSurveysList(2) //Get all surveys
                        customAdapterListView = CustomAdapterAllSurveys((activity as AdminSurveyActivity).applicationContext, surveys)
                    }
                    "Expired" -> {
                        surveys = myFun.getFilteredSurveysList(3) //Get all surveys
                        customAdapterListView = CustomAdapterAllSurveys((activity as AdminSurveyActivity).applicationContext, surveys)
                    }
                }
                filteredSurveysList.adapter = customAdapterListView
                filteredSurveysList.isClickable = true
                filteredSurveysList.setOnItemClickListener { adapterView, view, position, l ->
                    survey = surveys[position]
                    popupWindow.showAtLocation(filteredSurveysList, Gravity.CENTER, 0, 0)
                    popupText = layoutInflater.findViewById(R.id.popupPrat)
                    popupText.text = view.context.getString(R.string.participants, myFun.numOfPraticipants(survey.id))
                }
                btnUpdate.setOnClickListener {
                    val intent = Intent((activity as AdminSurveyActivity), UpdateSurveyActivity::class.java)
                    intent.putExtra("survey", survey)
                    startActivity(intent)
                }
                btnAddQuestions.setOnClickListener {
                    val intent = Intent((activity as AdminSurveyActivity), AddQuestionsActivity::class.java)
                    intent.putExtra("survey", survey)
                    startActivity(intent)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        return view
    }
}