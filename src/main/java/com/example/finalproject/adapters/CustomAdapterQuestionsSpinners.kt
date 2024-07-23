package com.example.finalproject.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.finalproject.R
import com.example.finalproject.model.Questions

class CustomAdapterQuestionsSpinners (context: Context, private val questionList: ArrayList<Questions>) :
    ArrayAdapter<Questions>(context, R.layout.activity_dropdown_menu_item, questionList) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return questionList.size
    }

    override fun getItem(position: Int): Questions {
        return questionList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.activity_dropdown_menu_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.text.text = questionList[position].question
        return view
    }
    private inner class ViewHolder(view: View) {
        val text: TextView = view.findViewById(R.id.dropdown_menu_item)
    }
}