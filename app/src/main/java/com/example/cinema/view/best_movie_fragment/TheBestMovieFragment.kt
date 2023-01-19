package com.example.cinema.view.best_movie_fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.FragmentTheBestMovieBinding
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.utils.Extensions
import com.example.cinema.view.details_fragment.DetailsFragment
import com.example.cinema.app.AppState
import com.example.cinema.viewmodel.DetailsFragmentViewModel
import com.example.cinema.viewmodel.TheBestMovieViewModel

const val BUNDLE_TYPE = "Type"
const val BUNDLE_TITLE = "Title"

class TheBestMovieFragment : Fragment() {


    private var _binding: FragmentTheBestMovieBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle): TheBestMovieFragment {
            val fragment = TheBestMovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: TheBestMovieViewModel by lazy {
        ViewModelProvider(this).get(TheBestMovieViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type: Int = (arguments?.getInt(BUNDLE_TYPE) ?: Int) as Int
        val typeTitle: String = (arguments?.getString(BUNDLE_TITLE) ?: String) as String

        viewModel.getDataFromRemoteSource(type)
        val observer = Observer<AppState> {
            renderData(it, typeTitle)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun renderData(appState: AppState, typeTitle: String) {

        when (appState) {
            is AppState.Success -> {
                val movieDTO = appState.AboutMovieData
                showData(movieDTO, typeTitle)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.apply {
                    loadingLayout.visibility = View.GONE

                    Extensions.showSnackbar(
                        mainView,
                        getString(R.string.error),
                        getString(R.string.reload),
                        { viewModel.liveDataToObserveUpdate() }
                    )
                }
            }
        }
    }

    private lateinit var adapter_main: TheBestMovieFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentTheBestMovieBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showData(movieDTO: MovieDTO, typeTitle: String) {
        var AboutMovieData = movieDTO.docs
        with(binding) {
            titleRatings.text = typeTitle
            loadingLayout.visibility = View.GONE
            adapter_main = initAdapter()
            adapter_main.setAboutMovie(AboutMovieData)

            val recyclerViewFavorite: RecyclerView = recyclerViewLinesTheBest
            recyclerViewFavorite.apply {
                layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )
                recycledViewPool.clear()
                adapter = adapter_main
            }

        }
    }


    private fun initAdapter(): TheBestMovieFragmentAdapter {

        return TheBestMovieFragmentAdapter(object :
            TheBestMovieFragmentAdapter.OnItemViewClickListener {

            override fun onItemClick(aboutMovie: Docs) {
                val model = ViewModelProviders.of(requireActivity())
                    .get(DetailsFragmentViewModel::class.java)
                model.select(aboutMovie)
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, DetailsFragment.newInstance(Bundle().apply {

                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }, object : TheBestMovieFragmentAdapter.LikeClickListener {
            override fun onLikeClick(like: Boolean, aboutMovieItem: Docs) {
                viewModel.changeLikeDataInDB(like, aboutMovieItem)
            }

        }, viewModel)
    }


}