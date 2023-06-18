package com.example.moviedb


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


// Interacts with the database

@Dao
interface MovieDAO {

    // it searches the primary key, which in our case is the city name

    @Query("SELECT * FROM movieTable")
    fun getAll(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: Movie)
}

