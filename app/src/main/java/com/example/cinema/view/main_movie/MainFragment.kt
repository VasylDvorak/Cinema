package com.example.cinema.view.main_movie

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.view.Extensions
import com.example.cinema.view.MainActivity.Companion.start_cinema
import com.example.cinema.view.details.DetailsFragment
import com.example.cinema.viewmodel.AppState
import com.example.cinema.viewmodel.DetailsFragmentViewModel
import com.example.cinema.viewmodel.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this).get(MainFragmentViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            upcoming.text = getString(R.string.upcoming)
            nowPlaying.text = getString(R.string.now_playing)
        }


        viewModel.getNowPlayingFromDataBase(requireContext())
        val observer3 = Observer<MovieDTO> { movie ->

            showDataNowPlaying(movie)
        }
        viewModel.getDataNowPlaying().observe(viewLifecycleOwner, observer3)

        viewModel.getFromDataBase(requireContext())

        val observer = Observer<AppState> {

            renderData(it)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)


    }


    private lateinit var adapter: MainFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @SuppressLint("SuspiciousIndentation")
    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Success -> {
                val movieDTO = appState.AboutMovieData

                showData(movieDTO)


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
                        { viewModel.getDataFromLocalSource(context) }
                    )
                }
            }
        }
    }

    private fun showData(movieDTO: MovieDTO) {
        adapter = initAdapter()
        var AboutMovieData = movieDTO
        with(binding) {
            loadingLayout.visibility = View.GONE


            adapter.setAboutMovie(AboutMovieData.docs)

            recyclerViewLinesUpcoming.also { rw ->
                rw.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false
                )
                rw.recycledViewPool.clear()
                rw.adapter = adapter

            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun showDataNowPlaying(movieDTO: MovieDTO) {
        adapter = initAdapter()
        with(binding) {

            adapter.setAboutMovie(movieDTO.docs)
            recyclerViewLinesNowPlaying.also { rw ->
                rw.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false
                )
                rw.recycledViewPool.clear()
                rw.adapter = adapter


            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchItem.apply {

            searchView.also {

                it.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))
                it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.getDataFromRemoteSource(query, context)
                        it.clearFocus()
                        it.setQuery("", false)
                        collapseActionView()
                        Extensions.showToast(
                            context!!,
                            resources.getString(R.string.looking_for) + " " + query
                        )
                        return true
                    }

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onQueryTextChange(newText: String?): Boolean {


                        return false
                    }
                })
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.about -> {

                Extensions.showAlertDialog(
                    this, R.string.about_program, R.string.message_dialog,
                    android.R.drawable.ic_menu_info_details, R.string.yes
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initAdapter(): MainFragmentAdapter {

        return MainFragmentAdapter(object : MainFragmentAdapter.OnItemViewClickListener {

            override fun onItemClick(aboutMovie: Docs) {
                val model = ViewModelProviders.of(requireActivity())
                    .get(DetailsFragmentViewModel::class.java)
                model.select(aboutMovie)
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, DetailsFragment.newInstance(Bundle()))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }, object : MainFragmentAdapter.LikeClickListener {
            override fun onLikeClick(like: Boolean, aboutMovieItem: Docs, context: Context) {
                viewModel.changeLikeDataInDB(like, aboutMovieItem, context)
            }
        }, viewModel)
    }


}