package com.example.cinema.view

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cinema.R
import com.example.cinema.databinding.FragmentPlayMovieBinding
import com.example.cinema.viewmodel.PlayViewModel

class PlayMovieFragment : Fragment() {
    private var _binding: FragmentPlayMovieBinding? = null
    private val binding get() = _binding!!


    private val viewModelPlay: PlayViewModel by lazy {
        ViewModelProvider(this).get(PlayViewModel::class.java)
    }


    companion object {
        const val BUNDLE_MOVIE = "BundleMovie"
        fun newInstance(bundle: Bundle): PlayMovieFragment {
            val fragment = PlayMovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlayMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private lateinit var aboutMovieBundle: Doc

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webview.visibility = View.GONE
        binding.videoView.visibility = View.GONE
        binding.idTVHeading.visibility = View.GONE

        aboutMovieBundle = arguments?.getParcelable(BUNDLE_MOVIE) ?: Doc()

        viewModelPlay.trailerMovie(aboutMovieBundle.id, requireContext())




        val observer = Observer<String> {
            val movie_point =
                (it[it.length - 4]).toString()
            if (movie_point == ".") {
                displayMovie(it, aboutMovieBundle.name)
            } else {
                displayWebPage(it)
            }
        }
        viewModelPlay.getPlayData().observe(viewLifecycleOwner, observer)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayWebPage(urlStr: String) {
        binding.webview.visibility = View.VISIBLE
        viewModelPlay.fromDetailFragment(urlStr)

        val observer = Observer<String> {
            binding.webview.loadDataWithBaseURL(
                null, it, "text/html; charset=utf-8", "utf-8", null
            )
        }
        viewModelPlay.getPlayData().observe(viewLifecycleOwner, observer)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayMovie(urlStr: String, name : String) {
        binding.videoView.visibility = View.VISIBLE
        binding.idTVHeading.visibility = View.VISIBLE
        try {
            with(binding) {
                idTVHeading.text = name
                val videoUrl = urlStr
                val uri: Uri = Uri.parse(videoUrl)
                videoView.setVideoURI(uri)
                val mediaController = MediaController(requireContext())
                mediaController.setAnchorView(videoView)
                mediaController.setMediaPlayer(videoView)
                videoView.setMediaController(mediaController)
                videoView.start()

            }
        } catch (e: IndexOutOfBoundsException) {
            Extensions.showSnackbar(binding.videoView,
                requireContext().resources.getString(R.string.Movie_could_not_find),
                requireContext().resources.getString(R.string.OK),
                { requireContext().resources.getString(R.string.OK) })
            fragmentManager?.popBackStack()
        }

    }
}
