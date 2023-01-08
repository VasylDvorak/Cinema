package com.example.cinema.view.ratings_movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinema.R
import com.example.cinema.databinding.FragmentRatingsBinding


class RatingsFragment : Fragment(), View.OnClickListener {


    private var b = false
    private var _binding: FragmentRatingsBinding? = null
    private val binding
        get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRatingsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.also{
            it.movie.setOnClickListener(this)
            it.movie.setOnClickListener(this)
            it.TVSeries.setOnClickListener(this)
            it.cartoon.setOnClickListener(this)
            it.anime.setOnClickListener(this)
            it.animatedSeries.setOnClickListener(this)
            it.TVShow.setOnClickListener(this)
            it.miniSeries.setOnClickListener(this)
        }

        binding.movie.setOnClickListener(this)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            RatingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(v: View?) {


        binding.also{
            resources.apply {
        when (v?.getId()) {
            it.movie.id -> showRatingsMovie(1, getString(R.string.movie_ratings))
            it.TVSeries.id -> showRatingsMovie(2, getString(R.string.tv_series_ratings))
            it.cartoon.id -> showRatingsMovie(3, getString(R.string.cartoon_ratings))
            it.anime.id -> showRatingsMovie(4, getString(R.string.anime_ratings))
            it.animatedSeries.id -> showRatingsMovie(5,
                getString(R.string.animated_series_ratings))
            it.TVShow.id -> showRatingsMovie(6, getString(R.string.tv_show_ratings))
            it.miniSeries.id -> showRatingsMovie(7, getString(R.string.mini_series_ratings))
        }
        }}
    }

    private fun showRatingsMovie(type: Int, string: String) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.flFragment, TheBestMovieFragment.newInstance(Bundle().apply {
                    putInt(BUNDLE_TYPE, type)
                    putString(BUNDLE_TITLE, string)
                }))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }


    }
}


