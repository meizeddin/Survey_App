package com.example.finalproject.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject.activities.BarChartStatsFragment
import com.example.finalproject.activities.PieChartStatsFragment
import com.example.finalproject.activities.SurveyFragment
import com.example.finalproject.activities.TableFragment


class CustomAdapterFragmentPager(val fragments: ArrayList<Fragment>, activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SurveyFragment()
            1 -> TableFragment()
            2 -> BarChartStatsFragment()
            else -> PieChartStatsFragment()
        }
    }

}