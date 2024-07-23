package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

import com.example.finalproject.R
import com.example.finalproject.adapters.CustomAdapterFragmentPager
import com.example.finalproject.cotrollers.Functions

class AdminSurveyActivity: AppCompatActivity() {
    private lateinit var myFun: Functions
    private lateinit var viewPager: ViewPager2
    private lateinit var fragments: ArrayList<Fragment>
    var adminID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_surveys)
        adminID = intent.getIntExtra("adminID",0)
        myFun = Functions(this)
        viewPager = findViewById(R.id.viewPager2)
        fragments = arrayListOf(
            SurveyFragment(),
            BarChartStatsFragment(),
            PieChartStatsFragment()
        )

        val customAdapter = CustomAdapterFragmentPager(fragments, this)
        viewPager.adapter = customAdapter

        //helloText = findViewById(R.id.txtSurveyLogin)
        //helloText.text = intent.getStringExtra("studentName")
    }

    fun btnLogout(view: View) {
        this@AdminSurveyActivity.finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun btnAddSurvey(view: View){
        finish()
        val intent = Intent(this, AddSurveyActivity::class.java)
        intent.putExtra("adminID", adminID)
        startActivity(intent)
    }
}