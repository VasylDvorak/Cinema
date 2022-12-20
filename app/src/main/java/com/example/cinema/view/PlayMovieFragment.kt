package com.example.cinema.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.example.cinema.R
import com.example.cinema.databinding.FragmentPlayMovieBinding
import com.example.cinema.model.gson_decoder.MovieDTO

class PlayMovieFragment : Fragment() {
    private var _binding: FragmentPlayMovieBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val BUNDLE_MOVIE = "BundleMovie"
        fun newInstance(bundle: Bundle): PlayMovieFragment {
            val fragment = PlayMovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlayMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private lateinit var aboutMovieBundle: MovieDTO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutMovieBundle = arguments?.getParcelable(BUNDLE_MOVIE) ?: MovieDTO()
        displayMovie(aboutMovieBundle)
    }

    private fun displayMovie(movieDTO: MovieDTO) {
        try {
            with(binding) {
                idTVHeading.text = movieDTO.docs[0].name

                var videoUrl =
                    "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"
                val uri: Uri = Uri.parse(videoUrl)
                videoView.setVideoURI(uri)
                val mediaController = MediaController(requireContext())
                mediaController.setAnchorView(videoView)
                mediaController.setMediaPlayer(videoView)
                videoView.setMediaController(mediaController)
                videoView.start()

            }
        } catch (e: IndexOutOfBoundsException) {
            Extensions.showSnackbar(
                binding.videoView,
                requireContext().resources.getString(R.string.Movie_could_not_find),
                requireContext().resources.getString(R.string.OK),
                { requireContext().resources.getString(R.string.OK) }
            )
            fragmentManager?.popBackStack()
        }

    }
}
