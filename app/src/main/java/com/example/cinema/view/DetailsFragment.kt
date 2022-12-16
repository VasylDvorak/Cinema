package com.example.cinema.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.cinema.R
import com.example.cinema.databinding.FragmentDetailsBinding
import com.example.cinema.model.AboutMovie

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

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<AboutMovie>(BUNDLE_EXTRA)?.let { aboutMovie ->

            aboutMovie.movie.also {
                with(binding) {
                    detailsTitleMovie.text = it.movie_title
                    detailsOriginalTitleMovie.text = it.original_title
                    detailsBannerMovie.setImageResource(it.picture)
                    detailsYearMovie.text = resources.getText(R.string.release_date)
                            as String + " " + aboutMovie.release_date
                    detailsRatingMovie.text = aboutMovie.rating
                    detailsGenreMovie.text = aboutMovie.genre
                    detailsDurationMovie.text = aboutMovie.duration
                    detailsBudgetMovie.text = resources.getText(R.string.budget)
                            as String + " " + aboutMovie.budget
                    detailsRevenueMovie.text = resources.getText(R.string.revenue)
                            as String + " " + aboutMovie.revenue
                    detailsDescriptionMovie.text = aboutMovie.description

                    var heart: ImageView = binding.detailsIsLikeMovie

                    heart.apply {

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

                        }

                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}