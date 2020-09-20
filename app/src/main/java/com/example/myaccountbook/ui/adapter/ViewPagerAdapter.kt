package com.example.myaccountbook.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myaccountbook.ui.fragment.CalendarFragment
import com.example.myaccountbook.ui.fragment.HomeFragment
import com.example.myaccountbook.ui.fragment.SettingsFragment

class ViewPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val TOTAL_PAGE_COUNT = 3
    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> CalendarFragment()
            1->HomeFragment()
            else->SettingsFragment()
        }
    }

    override fun getCount(): Int {
        return TOTAL_PAGE_COUNT
    }
}