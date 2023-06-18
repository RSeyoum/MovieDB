package com.example.moviedb


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movieTable")
class Movie {

    @PrimaryKey
    var movieId = 0
    var title = "title"
    var poster_img = "image"
    var rating = 0.0f
    var release_date = "release_date"
    var overview = "overview"
    var vote_average = 0.0


}