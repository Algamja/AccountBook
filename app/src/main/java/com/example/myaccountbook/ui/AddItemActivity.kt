package com.example.myaccountbook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProviders
import com.example.myaccountbook.R
import com.example.myaccountbook.database.AccountEntity
import com.example.myaccountbook.ui.viewmodel.AccountViewModel
import com.example.myaccountbook.util.AccountString
import kotlinx.android.synthetic.main.activity_add_item.*

class AddItemActivity : AppCompatActivity() {

    private lateinit var accountViewModel: AccountViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)

        if (intent != null
            && intent.hasExtra(AccountString().EXTRA_ACCOUNT_ID)
            && intent.hasExtra(AccountString().EXTRA_ACCOUNT_CATEGORY)
            && intent.hasExtra(AccountString().EXTRA_ACCOUNT_MONEY)
            && intent.hasExtra(AccountString().EXTRA_ACCOUNT_DATE)
        ) {
            add_item_input_money.setText(intent.getStringExtra(AccountString().EXTRA_ACCOUNT_MONEY))
            if (intent.getStringExtra(AccountString().EXTRA_ACCOUNT_CATEGORY) == AccountString().INCOME) {
                add_item_radio_button_income.isSelected = true
            } else {
                add_item_radio_button_pay.isSelected = true
            }
            id = intent.getLongExtra(AccountString().EXTRA_ACCOUNT_ID, -1)
        }

        add_item_add_button.setOnClickListener {
            val categoryId = add_item_radio_group.checkedRadioButtonId
            val category = findViewById<RadioButton>(categoryId).text.toString()
            val money = add_item_input_money.text.toString().trim().toInt()
            val date = intent.getStringExtra(AccountString().EXTRA_ACCOUNT_DATE)?:""

            val account = AccountEntity(id, category, money, date)
            accountViewModel.insertAccount(account)
            finish()
        }
    }
}
