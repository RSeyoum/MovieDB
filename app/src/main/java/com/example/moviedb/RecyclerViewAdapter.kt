package com.example.moviedb

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(var movieArray: Array<Movie>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    lateinit var onClick: (Movie) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return RecyclerViewHolder(viewItem, onClick)
    }

    override fun getItemCount(): Int {
        return movieArray.size

        Log.d("size_menu", movieArray.size.toString())
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(movieArray[position])
    }


    // Takes a parameter of a city
    class RecyclerViewHolder(val viewItem: View, val onClick: (Movie) -> Unit) : RecyclerView.ViewHolder(viewItem) {


        fun bind(movie: Movie) {
            viewItem.findViewById<TextView>(R.id.item_name).text = movie.title
            viewItem.findViewById<RatingBar>(R.id.ratingBar).rating = movie.rating

            // Glide.with(viewItem.context).load("https://image.tmdb.org/t/p/w154/nzGU9YcZYbusvIMAZzx13X38jey.jpg").into(viewItem.findViewById<ImageView>(R.id.editor_poster_img))

            Log.d("title", movie.title)

            // Image


            viewItem.setOnClickListener {
                onClick(movie)
            }
        }
    }
}