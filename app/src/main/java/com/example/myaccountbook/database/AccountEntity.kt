package com.example.myaccountbook.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var category :String,
    var money:Int,
    var date:String
)