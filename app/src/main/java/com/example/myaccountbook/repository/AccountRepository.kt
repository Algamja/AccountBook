package com.example.myaccountbook.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myaccountbook.database.AccountDatabase
import com.example.myaccountbook.database.AccountEntity
import java.lang.Exception

class AccountRepository(application: Application) {
    private val accountDatabase = AccountDatabase.getInstance(application)!!
    private val accountDao = accountDatabase.accountDao()

    fun getAccount(date: String): LiveData<List<AccountEntity>> {
        return accountDao.getAccount(date)
    }

    fun insertAccount(account:AccountEntity){
        try {
            val thread = Thread(Runnable {
                accountDao.insertAccount(account)
            })
            thread.start()
        } catch (exception: Exception) {
        }
    }

    fun deleteAccount(account:AccountEntity){
        try {
            val thread = Thread(Runnable {
                accountDao.deleteAccount(account)
            })
            thread.start()
        } catch (exception: Exception) {
        }
    }

    fun updateAccount(account: AccountEntity){
        try {
            val thread = Thread(Runnable {
                accountDao.updateAccount(account)
            })
            thread.start()
        } catch (exception: Exception) {
        }
    }
}