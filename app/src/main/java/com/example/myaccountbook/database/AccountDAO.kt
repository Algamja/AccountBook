package com.example.myaccountbook.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface AccountDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: AccountEntity)

    @Query("SELECT * FROM account WHERE date LIKE :date")
    fun getAccount(date: String): LiveData<List<AccountEntity>>

    @Delete
    fun deleteAccount(account:AccountEntity)

    @Update
    fun updateAccount(account:AccountEntity)
}