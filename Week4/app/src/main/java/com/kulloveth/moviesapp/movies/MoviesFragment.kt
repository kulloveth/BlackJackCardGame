package com.kulloveth.moviesapp.movies

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.moviesapp.MoviesDataManager
import com.kulloveth.moviesapp.databinding.FragmentMoviesBinding
import com.kulloveth.moviesapp.models.Movie


class MoviesFragment : Fragment(), MovieAdapter.MovieItemCLickedListener {

    lateinit var adapter: MovieAdapter
    lateinit var moviesDataManager: MoviesDataManager
    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentMoviesBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.contentLayout.toolbar.title = "Movies"
        moviesDataManager = ViewModelProvider(requireActivity()).get(MoviesDataManager::class.java)
        bindMoviesRecyclerView()

    }

    //bind data to adapter and recyclerview
    private fun bindMoviesRecyclerView() {
        adapter = MovieAdapter(this)
        recyclerView = binding.contentLayout.showMoviesRv
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireActivity())
        } else {

            val layoutManager =
                GridLayoutManager(requireActivity(), 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                 return  when (adapter.getItemViewType(position)) {
                        0 -> 2
                        else -> 1
                    }
                }
            }
            recyclerView.layoutManager = layoutManager
        }

        adapter.submitList(moviesDataManager.getMovieComposites())
        recyclerView.adapter = adapter
    }

    override fun movieItemCLicked(movie: Movie) {
        //used to get the clicked movie
        moviesDataManager.setUpMovie(movie)
        requireView().findNavController()
            .navigate(MoviesFragmentDirections.actionMovieListToMovieDetailFragment())
    }

}
