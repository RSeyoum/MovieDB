package com.example.moviedb

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class APIManager(val movieViewModel: MovieViewModel) {


        private val apiURL = "https://api.themoviedb.org/"
        private val apiKey = "706e222aa5f58bc850a618decb62b879"
        private val page = "1"

        val retrofit = Retrofit.Builder()

                .baseUrl(apiURL)
                .build()

        val service = retrofit.create(MovieService::class.java)

        interface MovieService {
                @GET("3/movie/now_playing?")
                fun getMovieByNowPlaying(
                        @Query("api_key") appid: String,
                        @Query("page") page: String
                ): Call<ResponseBody>

                @GET("3/movie/upcoming?")
                fun getMovieByUpComing(
                        @Query("api_key") appid: String,
                        @Query("page") page: String
                ): Call<ResponseBody>
        }

        fun decodeJson(json: String): Array<Movie> {

                // Movie data
                val data = JSONObject(json)

                // Use JSONObject and JSONArray to decode the raw file
                var raw_jsonArray = data.getJSONArray("results")

                // Movie list
                var movies = ArrayList<Movie>()

                Log.d("testSize", raw_jsonArray.length().toString())

                // Loop through JSONArray
                (0 until raw_jsonArray.length()).forEach {


                        val movie = Movie()

                        // Index to get each JSONObject
                        val array_index = raw_jsonArray.getJSONObject(it)

                        // Retrive data for each movie item
                        movie.movieId = array_index.getInt("id")
                        movie.title = array_index.getString("title")
                        movie.overview = array_index.getString("overview")
                        movie.release_date = array_index.getString("release_date")
                        movie.poster_img = array_index.getString("poster_path")
                        movie.vote_average = array_index.getDouble("vote_average")
                        movie.rating = 0.0f

                        // Add to movie array
                        movies.add(movie)

                        Log.d("title", movie.title)

                        // Add movie to currentMovie
                        // movieViewModel.setCurrentMovie(movie)
                }

                // movieViewModel.currentMovieList.value = movies.toTypedArray()
                Log.d("movies", movies.toTypedArray().toString())
                return movies.toTypedArray()

        }

        fun fetchNowPlayingMovies() {
                val call = service.getMovieByNowPlaying(apiKey, page)
                call.enqueue(MovieNowPlayingCallback())
        }

        fun fetchUpComingMovies() {
                val call = service.getMovieByUpComing(apiKey, page)
                call.enqueue(MovieUpcomingCallback())
        }


        inner class MovieNowPlayingCallback() :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("failed", t.message.toString())
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                        if (response.isSuccessful) {
                                response.body()?.let {
                                        //save to now playing in viewmodel
                                        movieViewModel.setNowPlayingMovieList(decodeJson(it.string()))

                                }
                        }
                }
        }

        inner class MovieUpcomingCallback() :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("failed2", t.message.toString())
                }

                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                        if (response.isSuccessful) {
                                response.body()?.let {
                                        //save to upcoming in viewmodel

                                        movieViewModel.setUpcomingMovieList(decodeJson(it.string()))

                                }
                        }
                }
        }


}