package com.example.cinema.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.MovieLoader
import com.example.cinema.model.Repository
import com.example.cinema.model.RepositoryImpl
import com.example.cinema.model.gson_decoder.MovieDTO

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
) : ViewModel() {
    val liveDataCurrent = MutableLiveData<MovieDTO>()
    fun getAboutMovie() = getDataFromLocalSource(true)
    fun getUpcomingMovie() = repositoryImpl.getAboutMovieLocalStorageUpcoming()

    private fun getDataFromLocalSource(isNowPlaying: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {

            liveDataToObserve.postValue(
                AppState.Success(
                    if (isNowPlaying) {
                        repositoryImpl.getAboutMovieLocalStorageNowPlaying()

                    } else {
                        repositoryImpl.getAboutMovieLocalStorageUpcoming()
                    }
                )
            )
        }.start()
    }

    fun getDataFromRemoteSource(find_request: String?, context: Context?) {

        var loader = MovieLoader(
            onLoadListener, find_request, context
        )
        loader.loadMovie()
        try {

        } catch (e: NullPointerException) {
        }
    }

    fun getData(): MutableLiveData<AppState> {
        return liveDataToObserve
    }

    private val onLoadListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {

            override fun onLoaded(movieDTO: MovieDTO) {
                repositoryImpl.getAboutMovieFromServer(movieDTO)
                getDataFromLocalSource(true)
            }

            override fun onFailed(throwable: Throwable) {
            }
        }
}