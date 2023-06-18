package com.example.moviedb

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {

    val apiManager = MutableLiveData<APIManager>()
    val upcomingMovie = MutableLiveData<Array<Movie>>()
    val nowplayingMovie = MutableLiveData<Array<Movie>>()
    val database = MutableLiveData<MovieDB>()

    // Current restaurant
    var currentMovie = MutableLiveData<Movie>()


    init {
        apiManager.value = APIManager(this)
        upcomingMovie.value = emptyArray()
        nowplayingMovie.value = emptyArray()
    }


    fun setNowPlayingMovieList(array:Array<Movie>){
        nowplayingMovie.value = array
        nowplayingMovie.postValue(array)
    }

    fun setUpcomingMovieList(array:Array<Movie>){
        upcomingMovie.value = array
        upcomingMovie.postValue(array)
    }

    fun getList(): Array<Movie> {

        // Holds all the entire list
        var movie_list = emptyArray<Movie>()

        if(upcomingMovie.value != null){
            movie_list = upcomingMovie.value!!.sortedByDescending { it.rating }.toTypedArray()
        }

        Log.d("entire_list", movie_list.toString())
        return movie_list

    }


    fun setCurrentMovie(movie: Movie){
        currentMovie.value = movie
        currentMovie.postValue(movie)
    }

}

