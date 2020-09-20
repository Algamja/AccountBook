package com.example.myaccountbook.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.myaccountbook.R
import com.example.myaccountbook.database.AccountEntity
import com.example.myaccountbook.ui.AddItemActivity
import com.example.myaccountbook.ui.adapter.AccountAdapter
import com.example.myaccountbook.ui.viewmodel.AccountViewModel
import com.example.myaccountbook.util.CalculateToday
import com.example.myaccountbook.util.AccountString
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import androidx.lifecycle.Observer

class HomeFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        setAccountHeader(rootView)
        rootView.home_add_button.setOnClickListener {
            val date = home_tv_date.text
            val intent = Intent(rootView.context, AddItemActivity::class.java)
            intent.putExtra(AccountString().EXTRA_ACCOUNT_DATE, date)
            startActivity(intent)
        }

        val adapter =
            AccountAdapter {
                val intent = Intent(rootView.context, AddItemActivity::class.java)
                intent.putExtra(
                    AccountString().EXTRA_ACCOUNT_ID, it.id
                )
                intent.putExtra(AccountString().EXTRA_ACCOUNT_CATEGORY, it.category)
                intent.putExtra(AccountString().EXTRA_ACCOUNT_MONEY, it.money)
                intent.putExtra(AccountString().EXTRA_ACCOUNT_DATE, it.date)
            }

        val layoutManager = LinearLayoutManager(rootView.context)
        rootView.home_recycler_view.adapter = adapter
        rootView.home_recycler_view.layoutManager = layoutManager
        rootView.home_recycler_view.setHasFixedSize(true)

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        accountViewModel.getAccount("%${rootView.home_tv_date.text}%")
            .observe(this, Observer<List<AccountEntity>>() {
                adapter.setAccounts(it)
            })
        return rootView
    }

    private fun setAccountHeader(rootView: View) {
        val year = CalculateToday().year
        val month = CalculateToday().month
        val day = CalculateToday().day

        rootView.home_tv_date.text = "$year / $month / $day"
    }
}
