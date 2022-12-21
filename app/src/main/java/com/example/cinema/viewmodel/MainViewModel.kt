package com.example.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.Repository
import com.example.cinema.model.RepositoryImpl
import com.example.cinema.model.gson_decoder.MovieDTO
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {
val liveDataCurrent = MutableLiveData <MovieDTO>()

    fun getLiveData() = liveDataToObserve
    fun getAboutMovie() = getDataFromLocalSource(true)
    fun getUpcomingMovie() = repositoryImpl.getAboutMovieLocalStorageUpcoming()

    private fun getDataFromLocalSource(isNowPlaying: Boolean) {
        liveDataToObserve.value = AppState.Loading

        Thread {
            sleep(1000)
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

}