package com.example.islamicapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MostRecently::class], version = 1, exportSchema = false)
abstract class MostRecentlyDataBase : RoomDatabase() {

    abstract fun getDao(): MostRecentlyDao

    companion object {
        @Volatile
        private var INSTANCE: MostRecentlyDataBase? = null

        fun getDataBase(context: Context): MostRecentlyDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MostRecentlyDataBase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}