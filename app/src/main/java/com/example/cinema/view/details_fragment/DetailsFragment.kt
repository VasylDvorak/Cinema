package com.example.cinema.view.details_fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.cinema.R
import com.example.cinema.databinding.FragmentDetailsBinding
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.utils.Extensions
import com.example.cinema.view.play_movie_fragment.PlayMovieFragment
import com.example.cinema.view.note_fragment.NoteFragment
import com.example.cinema.view.note_fragment.NoteFragment.Companion.DOCS_EXTRA
import com.example.cinema.viewmodel.DetailsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val model: DetailsFragmentViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(DetailsFragmentViewModel::class.java)
    }

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
        var can_show = true
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.getSelected().observe(viewLifecycleOwner, { item ->
            displayMovie(item)
        })
    }

    private fun displayMovie(docs_data: Docs) {
        try {
            with(binding) {
                noteCorrect.setOnClickListener {
                    callNote(docs_data)
                }

                mainView.visibility = View.VISIBLE
                detailsTitleMovie.text = docs_data.name
                detailsOriginalTitleMovie.text = docs_data.alternativeName

                var strr = ""
                docs_data.poster?.let {
                    strr = docs_data.poster.url

                    detailsBannerMovie.load(strr) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                }

                detailsBannerMovie.setOnClickListener {
                    callPlayMovie(docs_data)
                }

                detailsYearMovie.text = resources.getText(R.string.release_date)
                        as String + " " + docs_data.year.toString()

                docs_data.rating.let {
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
                    detailsGenreMovie.text = resources.getText(R.string.type)
                            as String + " " + type
                    detailsDurationMovie.text = movieLength.toString() + " " +
                            resources.getText(R.string.min)
                                    as String

                    detailsDescriptionMovie.text = description

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

    private fun callNote(docs_data: Docs) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.flFragment, NoteFragment.newInstance(Bundle().apply {
                    putParcelable(DOCS_EXTRA, docs_data)
                }))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun callPlayMovie(docs_data: Docs) {
        if (can_show) {
            model.addNowPlaying(docs_data)
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.flFragment, PlayMovieFragment.newInstance(Bundle().apply {
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    }
}























