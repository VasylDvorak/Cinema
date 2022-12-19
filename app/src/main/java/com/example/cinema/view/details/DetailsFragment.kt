package com.example.cinema.view.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.cinema.R
import com.example.cinema.databinding.FragmentDetailsBinding
import com.example.cinema.model.AboutMovie
import com.example.cinema.model.MovieDTO

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    companion object {
        const val BUNDLE_EXTRA = "AboutMovie"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private lateinit var aboutMovieBundle: AboutMovie
    private val onLoadListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {

            override fun onLoaded(movieDTO: MovieDTO) {
                displayMovie(movieDTO)
            }

            override fun onFailed(throwable: Throwable) {
                //Обработка ошибки
            }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutMovieBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: AboutMovie()

        binding.mainView.visibility = View.GONE
        binding.loadingLayout!!.visibility = View.VISIBLE

      //  aboutMovieBundle.movie.movie_title
        val loader = MovieLoader(onLoadListener, aboutMovieBundle.movie.movie_title)
        loader.loadMovie()
    }

    private fun displayMovie(movieDTO: MovieDTO) {


        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout!!.visibility = View.GONE
            detailsTitleMovie.text = movieDTO.name
            detailsOriginalTitleMovie.text = movieDTO.name
          //  detailsBannerMovie.setImageResource(it.picture)
            detailsYearMovie.text = resources.getText(R.string.release_date)
                 as String + " " + movieDTO.year.toString()
            detailsRatingMovie.text =movieDTO.year.toString()
            detailsGenreMovie.text = movieDTO.name
            detailsDurationMovie.text = movieDTO.year.toString()
            detailsBudgetMovie.text = resources.getText(R.string.budget)
                    as String + " " + movieDTO.year.toString()
            detailsRevenueMovie.text = resources.getText(R.string.revenue)
                    as String + " " + movieDTO.year.toString()
            detailsDescriptionMovie.text = movieDTO.description

            var heart: ImageView = binding.detailsIsLikeMovie

            heart.apply {

      /*
                if (!aboutMovie.isLike) {
                    setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                } else {
                    setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                }

                setOnClickListener {

                    if (aboutMovie.isLike) {
                        setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                        aboutMovie.isLike = false
                    } else {
                        setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                        aboutMovie.isLike = true
                    }
                    */

                }

            }
        }
    }
























