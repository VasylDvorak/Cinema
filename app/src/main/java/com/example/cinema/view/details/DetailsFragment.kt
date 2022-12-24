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
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.view.Extensions
import com.example.cinema.view.PlayMovieFragment
import com.squareup.picasso.Picasso


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


    private lateinit var aboutMovieBundle: Docs


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutMovieBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Docs()
        displayMovie(aboutMovieBundle)
    }

    private fun displayMovie(docs_data: Docs) {
        try {
            with(binding) {
                mainView.visibility = View.VISIBLE
                loadingLayout.visibility = View.GONE
                detailsTitleMovie.text = docs_data.name
                detailsOriginalTitleMovie.text = docs_data.alternativeName

                var strr = ""
                docs_data.poster?.let {
                    strr = docs_data.poster.url
                }

                Picasso.get().load(strr).into(detailsBannerMovie)
                detailsBannerMovie.setOnClickListener {
                    callPlayMovie(docs_data)
                }

                detailsYearMovie.text = resources.getText(R.string.release_date)
                        as String + " " + docs_data.year.toString()

                docs_data.rating?.let {
                    with(docs_data.rating) {
                        detailsRatingMovie.text = resources.getText(R.string.rating)
                                as String + " " + kp.toString()
                        detailsBudgetMovie.text = resources.getText(R.string.budget)
                                as String + " " +
                                (russianFilmCritics * 10000) + " $"
                        detailsRevenueMovie.text = resources.getText(R.string.revenue)
                                as String + " " + (await * 10000) + " $"
                    }
                }
                docs_data.apply {
                    detailsGenreMovie.text = resources.getText(R.string.genere)
                            as String + " " + type
                    detailsDurationMovie.text = movieLength.toString() + " " +
                            resources.getText(R.string.min)
                                    as String

                    detailsDescriptionMovie.text = description

                    var heart: ImageView = binding.detailsIsLikeMovie

                    heart.apply {

                        if (!isLike) {
                            setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                        } else {
                            setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                        }

                        setOnClickListener {

                            if (isLike) {
                                setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                                isLike = false
                            } else {
                                setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                                isLike = true
                            }
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

    private fun callPlayMovie(docs_data: Docs) {

        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.flFragment, PlayMovieFragment.newInstance(Bundle().apply {
                    putParcelable(PlayMovieFragment.BUNDLE_MOVIE, docs_data)
                }))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }

    }
}
























