package com.example.finalproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.finalproject.R
import com.example.finalproject.model.Survey
import com.example.finalproject.R.layout.activity_completed_surveys_listview_s

class CustomAdapterAllSurveys (appContext: Context, private val surveyList: ArrayList<Survey>) : BaseAdapter(){
    private val inflater: LayoutInflater = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return surveyList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if(convertView == null) {
            view = inflater.inflate(activity_completed_surveys_listview_s, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val title = surveyList[position].title
        val startDate = surveyList[position].startDate
        val endDate = surveyList[position].endDate
        val info = view.context.getString(R.string.survey_info, title, startDate, endDate)
        viewHolder.mSurvey.text = info
        return view
    }

    class ViewHolder(view: View) {
        val mSurvey: TextView = view.findViewById(R.id.textViewCompletedSurvey)
    }

}