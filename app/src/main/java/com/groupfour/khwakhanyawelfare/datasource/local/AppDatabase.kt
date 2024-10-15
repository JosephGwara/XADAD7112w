package com.groupfour.khwakhanyawelfare.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.groupfour.khwakhanyawelfare.datasource.entity.Donations

@Database([Donations::class], version = 1 )
abstract class AppDatabase :RoomDatabase(){

    companion object{
        fun getDatabase(context: Context):AppDatabase{
            return Room.databaseBuilder(context,AppDatabase::class.java,"khwakhanya.db")
                .allowMainThreadQueries()
                .build()
        }
    }
}