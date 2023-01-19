package com.example.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.app.App
import com.example.cinema.appliction_repository.Repository
import com.example.cinema.appliction_repository.RepositoryImpl
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.room_data_base.LocalRepositoryImpl

class FavoriteMovieFragmentViewModel(
    private val liveDataToObserve: MutableLiveData<MovieDTO> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDao())
) : ViewModel() {

    fun getDataFromDataBase() {

        try {
            Thread{
            var favorite_movie = historyRepositoryImpl.readFavoriteMovieMovieDTO()
            favorite_movie.let {
                liveDataToObserve.postValue(it)
            }}.start()

        } catch (e: NullPointerException) {
        }
    }

    fun getData(): MutableLiveData<MovieDTO> {
        return liveDataToObserve
    }

    fun changeLikeDataInDB(like: Boolean, aboutMovieItem: Docs) {

        if (like) {
            historyRepositoryImpl.addFavoriteMovie(aboutMovieItem)

        } else {
            historyRepositoryImpl.removeFavoriteMovie(aboutMovieItem)
        }

        try {
            Thread{
            var favorite_movie = historyRepositoryImpl.readFavoriteMovieMovieDTO()
            favorite_movie.let {
                repositoryImpl.setFavoriteMovie(it)
                liveDataToObserve.postValue(it)
            }}.start()
        } catch (e: NullPointerException) {
        }
    }

    fun getWatched(aboutMovieItem: Docs): Boolean {

        val watched = historyRepositoryImpl.watched(aboutMovieItem)

        return watched
    }
}