package com.example.cinema.view.favorite_movie

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFavoriteBinding
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.view.details.DetailsFragment
import com.example.cinema.viewmodel.DetailsFragmentViewModel
import com.example.cinema.viewmodel.FavoriteMovieFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class FavoriteMovieFragment : Fragment() {


    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: FavoriteMovieFragmentViewModel by lazy {
        ViewModelProvider(this).get(FavoriteMovieFragmentViewModel::class.java)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataFromDataBase(requireContext())
        val observer = Observer<MovieDTO> {
            showData(it)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }


    private lateinit var adapter_main: FavoriteMovieFragmentAdapter

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
        var AboutMovieData = movieDTO.docs
        with(binding) {
            loadingLayout.visibility = View.GONE
            adapter_main = initAdapter(AboutMovieData)
            adapter_main.setAboutMovie(AboutMovieData)

            val recyclerViewFavorite: RecyclerView = recyclerViewLinesFavorite
            recyclerViewFavorite.apply {
                layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )
                recycledViewPool.clear()
                adapter = adapter_main
            }

        }
    }


    private fun initAdapter(AboutMovieData: MutableList<Docs>): FavoriteMovieFragmentAdapter {

        return FavoriteMovieFragmentAdapter(object :
            FavoriteMovieFragmentAdapter.OnItemViewClickListener {

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
        }, object : FavoriteMovieFragmentAdapter.removeMovieListener {
            override fun removeMovieClick(
                like: Boolean, aboutMovieItem: Docs, context: Context, position: Int
            ) {

                viewModel.changeLikeDataInDB(like, aboutMovieItem, context)
                AboutMovieData.removeAt(position)
                adapter_main.notifyItemRemoved(position)
            }
        })
    }


}