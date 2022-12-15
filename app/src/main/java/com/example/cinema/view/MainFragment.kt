package com.example.cinema.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), CustomRecyclerAdapterNowPlaying.OnItemClickListenerNow,
    CustomRecyclerAdapterUpcoming.OnItemClickListenerUpcoming {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel


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

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<AppState> {
            renderData(it)
        }

        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getAboutMovie()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val AboutMovieData = appState.AboutMovieData

                binding.loadingLayout.visibility = View.GONE

                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()

                setData(AboutMovieData)
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE

                Snackbar.make(binding.mainView, "Error", Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.reload)) {
                        viewModel.getAboutMovie()
                    }
                    .show()
            }
        }
    }

    private fun setData(AboutMovieData: AboutMovie) {
        val recyclerViewNowPlaying: RecyclerView = binding.recyclerViewLinesNowPlaying
        recyclerViewNowPlaying.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        recyclerViewNowPlaying.adapter =
            CustomRecyclerAdapterNowPlaying(
                fillList(AboutMovieData.movie.movie_title),
                fillList(AboutMovieData.release_date), fillList(AboutMovieData.rating),
                fillListPicture(AboutMovieData.movie.picture), this
            )

        val recyclerViewUpcoming: RecyclerView = binding.recyclerViewLinesUpcoming
        recyclerViewUpcoming.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        recyclerViewUpcoming.adapter =
            CustomRecyclerAdapterUpcoming(
                fillList(AboutMovieData.movie.movie_title),
                fillList(AboutMovieData.release_date),
                fillListPicture(AboutMovieData.movie.picture), this
            )
        recyclerViewUpcoming.adapter
        binding.upcoming.text = getString(R.string.upcoming)
        binding.nowPlaying.text = getString(R.string.now_playing)
    }

    private fun fillList(str: String): List<String> {
        val data = mutableListOf<String>()
        (0..30).forEach { i -> data.add(str) }
        return data
    }

    private fun fillListPicture(pic: Int): List<Int> {
        val data = mutableListOf<Int>()
        (0..30).forEach { i -> data.add(pic) }
        return data
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(
                    context,
                    "Looking for $query", Toast.LENGTH_LONG
                ).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(
                    context,
                    "Looking for $newText", Toast.LENGTH_LONG
                ).show()
                return false
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.about -> {
                val builder = AlertDialog.Builder(requireContext())
                //set title for alert dialog
                builder.setTitle(R.string.about_program)
                //set message for alert dialog
                builder.setMessage(R.string.message_dialog)
                builder.setIcon(android.R.drawable.ic_menu_info_details)
                builder.setPositiveButton(R.string.yes) { dialogInterface, which ->
                    builder
                }
                builder.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClickNowPlaying(position: Int) {
        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.flFragment, ThirdFragment())
            commit()
        }
    }

    override fun onItemClickUpcoming(position: Int) {
        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.flFragment, ThirdFragment())
            commit()
        }
    }


}