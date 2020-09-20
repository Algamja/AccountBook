package com.example.myaccountbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AccountEntity::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDAO

    companion object{
        private var INSTANCE :AccountDatabase? = null

        fun getInstance(context: Context):AccountDatabase?{
            if(INSTANCE == null){
                synchronized(AccountDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AccountDatabase::class.java,
                        "account"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}