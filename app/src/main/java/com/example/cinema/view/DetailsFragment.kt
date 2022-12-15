package com.example.cinema.view

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aboutMovie = arguments?.getParcelable<AboutMovie>(BUNDLE_EXTRA)
        if (aboutMovie != null) {
            binding.detailsTitleMovie.text = aboutMovie.movie.movie_title
            binding.detailsOriginalTitleMovie.text = aboutMovie.movie.original_title
            binding.detailsYearMovie.text = resources.getText(R.string.release_date)
                    as String + " " + aboutMovie.release_date
            binding.detailsRatingMovie.text = aboutMovie.rating
            binding.detailsBannerMovie.setImageResource(aboutMovie.movie.picture)
            binding.detailsGenreMovie.text = aboutMovie.genre
            binding.detailsDurationMovie.text = aboutMovie.duration
            binding.detailsBudgetMovie.text = resources.getText(R.string.budget)
                    as String + " " + aboutMovie.budget
            binding.detailsRevenueMovie.text = resources.getText(R.string.revenue)
                    as String + " " + aboutMovie.revenue
            binding.detailsDescriptionMovie.text = aboutMovie.description
            var heart: ImageView = binding.detailsIsLikeMovie
            if (!aboutMovie.isLike) {
                heart.setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
            } else {
                heart.setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
            }
            heart.setOnClickListener {
                if (aboutMovie.isLike) {
                    heart.setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                    aboutMovie.isLike = false
                } else {
                    heart.setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                    aboutMovie.isLike = true
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}