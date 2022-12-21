package com.example.cinema.view.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cinema.BuildConfig
import com.example.cinema.R
import com.example.cinema.databinding.FragmentDetailsBinding
import com.example.cinema.model.AboutMovie
import com.example.cinema.model.gson_decoder.MovieDTO
import com.example.cinema.view.Extensions
import com.example.cinema.view.PlayMovieFragment
import com.example.cinema.viewmodel.MainViewModel
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val model: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)

    }

    companion object {
        const val BUNDLE_EXTRA = "AboutMovie"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun updateCurrentCard() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) { item ->
            displayMovie(item)
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


            override fun onLoaded() {
                try {
                    updateCurrentCard()
                } catch (e: NullPointerException) {
                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.flFragment, DetailsFragment())
                        addToBackStack("")
                        commit()
                    }
                }
            }


            override fun onFailed(throwable: Throwable) {
                Extensions.showSnackbar(
                    binding.mainView,
                    context!!.resources.getString(R.string.error),
                    context!!.resources.getString(R.string.OK),
                    { context!!.resources.getString(R.string.OK) }
                )
            }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutMovieBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: AboutMovie()

        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE

        val url = "https://api.kinopoisk.dev/movie?field" +
                "=name&search=${aboutMovieBundle.movie.movie_title}&isStrict=false&" +
                "token=${BuildConfig.KINOPOISK_API_KEY}"

        val loader = MovieLoader(onLoadListener, url, context, this, binding.mainView)
        loader.loadMovie()
    }

    private fun displayMovie(movieDTO: MovieDTO) {
        try {
            with(binding) {
                mainView.visibility = View.VISIBLE
                loadingLayout.visibility = View.GONE
                detailsTitleMovie.text = movieDTO.docs[0].name
                detailsOriginalTitleMovie.text = movieDTO.docs[0].alternativeName
                var strr: String = movieDTO.docs[0].poster.url
                Picasso.get().load(strr).into(detailsBannerMovie)
                detailsBannerMovie.setOnClickListener {
                    callPlayMovie(movieDTO)
                }
                //  detailsBannerMovie.setImageResource(it.picture)
                detailsYearMovie.text = resources.getText(R.string.release_date)
                        as String + " " + movieDTO.docs[0].year.toString()
                detailsRatingMovie.text = resources.getText(R.string.rating)
                        as String + " "+movieDTO.docs[0].rating.kp.toString()
                detailsGenreMovie.text = resources.getText(R.string.genere)
                        as String + " "+movieDTO.docs[0].type
                detailsDurationMovie.text = movieDTO.docs[0].movieLength.toString()+" "+
                        resources.getText(R.string.min)
                                as String
                detailsBudgetMovie.text = resources.getText(R.string.budget)
                        as String + " "+
                        (movieDTO.docs[0].rating.russianFilmCritics*10000).toString()+" $"
                detailsRevenueMovie.text = resources.getText(R.string.revenue)
                        as String + " " + (movieDTO.docs[0].rating.await*10000).toString()+" $"
                detailsDescriptionMovie.text = movieDTO.docs[0].description

                var heart: ImageView = binding.detailsIsLikeMovie

                heart.apply {

                    if (!aboutMovieBundle.isLike) {
                        setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                    } else {
                        setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                    }

                    setOnClickListener {

                        if (aboutMovieBundle.isLike) {
                            setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                            aboutMovieBundle.isLike = false
                        } else {
                            setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                            aboutMovieBundle.isLike = true
                        }
                    }
                }


            }

        } catch (e: IndexOutOfBoundsException) {
            Extensions.showSnackbar(
                binding.mainView,
                requireContext().resources.getString(R.string.Movie_could_not_find),
                requireContext().resources.getString(R.string.OK),
                { requireContext().resources.getString(R.string.OK) }
            )
            fragmentManager?.popBackStack()
        }
    }

    private fun callPlayMovie(movieDTO: MovieDTO) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.flFragment, PlayMovieFragment.newInstance(Bundle().apply {
                    putParcelable(PlayMovieFragment.BUNDLE_MOVIE, movieDTO)
                }))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }

    }
}
























