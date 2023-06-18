package com.example.moviedb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var viewManger: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerViewAdapter
    lateinit var movie_recyclerView: RecyclerView


    val viewModel: MovieViewModel by activityViewModels<MovieViewModel>()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle item selection
        val viewAdapter = RecyclerViewAdapter(viewModel.getList())

        // Checks which item is being clicked
        if (item.itemId == R.id.now_playing) {

            viewModel.apiManager.value?.fetchNowPlayingMovies()
            // viewAdapter.movieArray = viewModel.getList().sortedByDescending { it.rating }.toTypedArray()
            viewAdapter.notifyDataSetChanged()
            return true
        }
        else if (item.itemId == R.id.up_coming) {

            viewModel.apiManager.value?.fetchUpComingMovies()
            // viewAdapter.movieArray = viewModel.getList().sortedByDescending { it.rating }.toTypedArray()
            viewAdapter.notifyDataSetChanged()
            return true
        }
        else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManger = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = RecyclerViewAdapter(viewModel.getList())

        val r = viewModel.getList().size.toString()
        Log.d("list_viewmodel", r)

        movie_recyclerView = view.findViewById(R.id.list_recyclerView)

        movie_recyclerView.layoutManager = viewManger
        movie_recyclerView.adapter = viewAdapter

        viewModel.database.value = MovieDB.getDBObject(requireContext())

        viewModel.apiManager.value?.fetchUpComingMovies()



        viewModel.upcomingMovie.observe(viewLifecycleOwner, {

            viewAdapter.movieArray = viewModel.getList()
            viewAdapter.notifyDataSetChanged()

            Log.d("viewadapter", viewAdapter.movieArray.size.toString())
        })




        val onClickLambda : (Movie) -> Unit = {
            viewModel.setCurrentMovie(it)

            findNavController().navigate(R.id.action_global_detailFragment)
        }
        viewAdapter.onClick = onClickLambda

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
