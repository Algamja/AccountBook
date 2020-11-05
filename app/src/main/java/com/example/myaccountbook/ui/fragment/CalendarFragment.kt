package com.example.myaccountbook.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.myaccountbook.R
import com.example.myaccountbook.database.AccountEntity
import com.example.myaccountbook.ui.AddItemActivity
import com.example.myaccountbook.ui.adapter.CalendarAdapter
import com.example.myaccountbook.ui.viewmodel.AccountViewModel
import com.example.myaccountbook.util.AccountString
import com.example.myaccountbook.util.CalculateToday
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.item_calendar.view.*
import java.util.*
import kotlin.math.absoluteValue

class CalendarFragment : Fragment() {

    private val dayList = ArrayList<String>()
    private lateinit var accountViewModel: AccountViewModel
    private val money = mutableMapOf<String, Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =
            inflater.inflate(R.layout.fragment_calendar, container, false)

        var month = 0

        calculateMoney(month, rootView)

        rootView.prev_month.setOnClickListener {
            month--
            calculateMoney(month, rootView)
        }

        rootView.next_month.setOnClickListener {
            month++
            calculateMoney(month, rootView)
        }

        return rootView
    }

    private fun calculateMonthAndYear(month: Int): List<Int> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, month)
        val curMonth = calendar.get(Calendar.MONTH) + 1
        val curYear = calendar.get(Calendar.YEAR)
        return listOf<Int>(curMonth, curYear)
    }

    private fun calculateMoney(month: Int, rootView: View) {
        val monthAndYear = calculateMonthAndYear(month)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        accountViewModel.getAccount("%${monthAndYear[1]}년 ${monthAndYear[0]}월%")
            .observe(this, androidx.lifecycle.Observer<List<AccountEntity>> {
                money.clear()
                it.forEach { accountEntity ->
                    val date = accountEntity.date.split("${monthAndYear[1]}년 ${monthAndYear[0]}월 ")
                    if (date.size == 2) {
                        val dateString = date[1]
                        if (money[dateString] == null) {
                            money[dateString] = 0
                        }
                        if (accountEntity.category == AccountString().INCOME) {
                            money[dateString] = ((money[dateString] ?: 0).plus(accountEntity.money))
                        } else {
                            money[dateString] = (money[dateString] ?: 0).minus(accountEntity.money)
                        }

                    }
                }
                setCalendar(month, rootView, money)
            })
    }

    private fun setCalendarDate(month: Int, mCal: Calendar) {
        mCal.set(Calendar.MONTH, month - 1)

        for (i in 0 until mCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            dayList.add("${i + 1}")
        }
    }

    private fun setCalendar(month: Int, rootView: View, moneyDate: MutableMap<String, Int>?) {
        dayList.clear()

        val monthAndYear = calculateMonthAndYear(month)

        rootView.tv_date.text = "${monthAndYear[1]}년 ${monthAndYear[0]}월"

        val mCal = Calendar.getInstance()
        mCal.set(monthAndYear[1], monthAndYear[0] - 1, 1)
        val dayNum = mCal.get(Calendar.DAY_OF_WEEK)

        for (i in 1 until dayNum) {
            dayList.add("")
        }
        setCalendarDate(mCal.get(Calendar.MONTH) + 1, mCal)

        val adapter = CalendarAdapter(rootView.context, dayList, moneyDate, "${monthAndYear[1]}년 ${monthAndYear[0]}월")
        rootView.gridview.adapter = adapter
    }
}
