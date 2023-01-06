package com.example.cinema.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFavoriteBinding
import com.example.cinema.model.data_base.DBHelper
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.view.details.DetailsFragment
import com.example.cinema.viewmodel.DetailsFragmentViewModel
import com.example.cinema.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class FavoriteMovieFragment : Fragment() {


    private var _binding: FragmentFavoriteBinding? = null
    private val binding
    get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        retainInstance = true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            var dbHelper = DBHelper(requireContext(), null)
        try {
            var favorite_movie = dbHelper.readFavoriteMovieMovieDTO()
            favorite_movie?.let {
                showData(it)
            }
        }catch (e : NullPointerException){}
        dbHelper.close()
    }


    private lateinit var adapter: FavoriteMovieFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)



        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    private fun showData(movieDTO: MovieDTO) {
        var AboutMovieData = movieDTO
        with(binding) {
            loadingLayout.visibility = android.view.View.GONE

            adapter = initAdapter()

            adapter.setAboutMovie(AboutMovieData.docs, false)

            val recyclerViewNowPlaying: RecyclerView = recyclerViewLinesNowPlaying

            recyclerViewNowPlaying.layoutManager = LinearLayoutManager(
                context,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false
            )
            recyclerViewNowPlaying.recycledViewPool.clear()
            recyclerViewNowPlaying.adapter = adapter


        }
    }





    private fun initAdapter(): FavoriteMovieFragmentAdapter {

        return FavoriteMovieFragmentAdapter(object : FavoriteMovieFragmentAdapter.OnItemViewClickListener {

            override fun onItemClick(aboutMovie: Docs) {
                val model = ViewModelProviders.of(requireActivity()).get(DetailsFragmentViewModel::class.java)
                model.select(aboutMovie)
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, DetailsFragment.newInstance(Bundle().apply {

                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        },object : FavoriteMovieFragmentAdapter.removeMovieListener{
            override fun removeMovieClick(like: Boolean, aboutMovieItem: Docs, context: Context) {
                viewModel.changeLikeDataInDB(like,aboutMovieItem, context)
            }
        })
    }




}