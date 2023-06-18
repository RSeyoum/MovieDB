package com.example.moviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import android.widget.RatingBar.OnRatingBarChangeListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var detail_title: TextView
    lateinit var detail_release: TextView
    lateinit var detail_rating: TextView
    lateinit var detail_overview: TextView
    lateinit var detail_ratingBar: RatingBar
    lateinit var detail_img: ImageView

    val viewModel: MovieViewModel by activityViewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detail_title = view.findViewById(R.id.title_text)
        detail_release = view.findViewById(R.id.release_text)
        detail_rating = view.findViewById(R.id.rating_text)
        detail_overview = view.findViewById(R.id.overview_text)
        detail_ratingBar = view.findViewById(R.id.detail_ratingBar)
        detail_img = view.findViewById(R.id.editor_poster_img)

        // Get current movie object
        val currentMovie = viewModel.currentMovie.value!!

        detail_title.text = currentMovie.title
        detail_release.text = currentMovie.release_date
        detail_rating.text = "Rating: " + currentMovie.vote_average.toString()
        detail_overview.text = currentMovie.overview
        detail_ratingBar.rating = currentMovie.rating


        detail_ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { detail_rating, rating, userRating ->
                viewModel.currentMovie.value!!.rating = rating
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}