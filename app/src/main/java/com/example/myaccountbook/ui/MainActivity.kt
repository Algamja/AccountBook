package com.example.myaccountbook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.myaccountbook.R
import com.example.myaccountbook.ui.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
        main_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                main_navigation.menu.getItem(position).isChecked
            }
        })

        main_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.calendar -> main_view_pager.currentItem = 0
                R.id.home -> main_view_pager.currentItem = 1
            }
            true
        }
    }
}
