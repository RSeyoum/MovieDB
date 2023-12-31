package com.example.moviedb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Every time you update the city class you need to update the version

// Stores City class in database
@Database(entities = [Movie::class], version = 1)
abstract class MovieDB : RoomDatabase() {

    // returns movieDAO class
    abstract fun movieDAO(): MovieDAO

    companion object {
        private var INSTANT: MovieDB? = null

        fun getDBObject(context: Context): MovieDB? {
            if (INSTANT == null) {
                synchronized(MovieDB::class.java) {
                    INSTANT = Room.databaseBuilder(context, MovieDB::class.java, "movieDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }

}