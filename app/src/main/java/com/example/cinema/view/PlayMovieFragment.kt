package com.example.cinema.view

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.example.cinema.R
import com.example.cinema.databinding.FragmentPlayMovieBinding
import com.example.cinema.model.gson_decoder.Docs
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

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

    private lateinit var aboutMovieBundle: Docs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutMovieBundle = arguments?.getParcelable(BUNDLE_MOVIE) ?: Docs()

        binding.webview.visibility = View.GONE
        binding.videoView.visibility = View.GONE
        binding.idTVHeading.visibility = View.GONE


        val movie_point =
            (aboutMovieBundle.url_trailer[aboutMovieBundle.url_trailer.length - 4]).toString()

        if (movie_point == ".") {
            displayMovie(aboutMovieBundle)
        } else {
            displayWebPage(aboutMovieBundle)
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayWebPage(docs: Docs) {
        binding.webview.visibility = View.VISIBLE

        try {

            val uri = URL(docs.url_trailer)
            val handler = Handler() //Запоминаем основной поток
            Thread {
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET" //установка метода получения данных — GET
                    urlConnection.readTimeout = 10000 //установка таймаута — 10 000 миллисекунд
                    val reader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream)) //читаем данные в поток
                    val result = getLines(reader) // Возвращаемся к основному потоку
                    handler.post {
                        binding.webview.loadDataWithBaseURL(
                            null, result, "text/html; charset=utf-8",
                            "utf-8", null
                        )
                    }
                } catch (e: Exception) {

                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()

                }
            }.start()


        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayMovie(docs_data: Docs) {
        binding.videoView.visibility = View.VISIBLE
        binding.idTVHeading.visibility = View.VISIBLE
        try {
            with(binding) {
                idTVHeading.text = docs_data.name


                val videoUrl = docs_data.url_trailer
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
