package com.example.cinema.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.app.App
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.retrofit.DetailsRepository
import com.example.cinema.model.retrofit.DetailsRepositoryImpl
import com.example.cinema.model.retrofit.RemoteDataSource
import com.example.cinema.model.room_data_base.LocalRepositoryImpl

class DetailsFragmentViewModel(
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepositoryImpl: LocalRepositoryImpl
    = LocalRepositoryImpl(App.getHistoryDao())
) : ViewModel() {
    private val selected: MutableLiveData<Docs> = MutableLiveData<Docs>()

    fun select(docs: Docs) {
        val handler = Handler()
        Thread {
            val answer= detailsRepositoryImpl.getPlayMovieDetails(docs.id)
            docs.url_trailer = answer[0]
            docs.country = answer[1]
            handler.post { urlTrailer(docs)}
        }.start()
    }

    private fun urlTrailer(docs: Docs) {
        selected.value = docs
    }

    fun getSelected(): LiveData<Docs> {
        return selected
    }

    fun addNowPlaying(docsData: Docs) {

        historyRepositoryImpl.addNowPlayingMovieDTO(docsData)

    }
}