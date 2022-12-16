package com.example.cinema.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.model.AboutMovie
import com.example.cinema.viewmodel.AppState
import com.example.cinema.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val observer = Observer<AppState> {
            renderData(it)
        }

        with(viewModel) {
            getLiveData().observe(viewLifecycleOwner, observer)
            getAboutMovie()
        }

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val AboutMovieData = appState.AboutMovieData

                with(binding) {
                    loadingLayout.visibility = View.GONE
                    adapter = initAdapter()
                    adapter.setAboutMovie(AboutMovieData, false)

                    val recyclerViewNowPlaying: RecyclerView = recyclerViewLinesNowPlaying

                    recyclerViewNowPlaying.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL, false
                    )
                    recyclerViewNowPlaying.adapter = adapter

                    val recyclerViewUpcoming: RecyclerView = recyclerViewLinesUpcoming

                    recyclerViewUpcoming.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL, false
                    )
                    adapter = initAdapter()
                    adapter.setAboutMovie(viewModel.getUpcomingMovie(), true)
                    recyclerViewUpcoming.adapter = adapter

                    upcoming.text = getString(R.string.upcoming)
                    nowPlaying.text = getString(R.string.now_playing)
                }

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
                        { viewModel.getAboutMovie() }
                    )
                }
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
                        it.clearFocus()
                        it.setQuery("", false)
                        collapseActionView()
                        Extensions.showToast(mainView, query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        Extensions.showToast(mainView, newText)
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

            override fun onItemClick(aboutMovie: AboutMovie) {

                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, DetailsFragment.newInstance(Bundle().apply {
                            putParcelable(DetailsFragment.BUNDLE_EXTRA, aboutMovie)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }

            }

        })
    }

}