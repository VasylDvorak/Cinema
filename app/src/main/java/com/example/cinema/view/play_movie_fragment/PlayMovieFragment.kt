package com.example.cinema.view.play_movie_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.cinema.databinding.FragmentPlayMovieBinding
import com.example.cinema.viewmodel.DetailsFragmentViewModel


class PlayMovieFragment : Fragment() {
    private var _binding: FragmentPlayMovieBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle): PlayMovieFragment {
            val fragment = PlayMovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPlayMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
   //     activity.bottomNavigationView?.setVisibility(View.VISIBLE)
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   getActivity()?.bottomNavigationView?.setVisibility(View.GONE)
        val model =
            ViewModelProviders.of(requireActivity()).get(DetailsFragmentViewModel::class.java)
        model.getSelected().observe(viewLifecycleOwner) { item ->
            val webView: WebView = binding.webview
            val settings: WebSettings = webView.settings
            webView.loadUrl(item.url_trailer)
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(true)
                builtInZoomControls = true
            }
        }
    }
}
