package com.example.myaccountbook.ui.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.myaccountbook.R
import kotlinx.android.synthetic.main.item_calendar.view.*
import java.util.*

class CalendarAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val moneyDate: MutableMap<String, Int>?
) :
    BaseAdapter() {

    private var inflater: LayoutInflater =
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)) as LayoutInflater

    override fun getView(position: Int, contentView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (contentView == null) {
            view = inflater.inflate(R.layout.item_calendar, parent, false)
            holder = ViewHolder()
            holder.tvItemGridView = view.tv_item_gridview
            holder.layoutItemGridView = view.item_calendar_layout
            holder.tvMoneyItemGridView = view.item_calendar_money
            view.tag = holder
        } else {
            holder = (contentView.tag) as ViewHolder
            view = contentView
        }
        holder.tvItemGridView.text = getItem(position).toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            (moneyDate ?: mutableMapOf("" to 0)).forEach { (date, money) ->
                if (date == getItem(position).toString()) {
                    holder.tvMoneyItemGridView.text = money.toString()

                    if(money >=0){
                        holder.tvMoneyItemGridView.setTextColor(ContextCompat.getColor(context, R.color.colorIncome))
                    }
                    else{
                        holder.tvMoneyItemGridView.setTextColor(ContextCompat.getColor(context, R.color.colorPay))
                    }
                }
            }
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}

class ViewHolder() {
    lateinit var tvItemGridView: TextView
    lateinit var layoutItemGridView: ConstraintLayout
    lateinit var tvMoneyItemGridView:TextView
}