package com.example.cinema.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.Repository
import com.example.cinema.model.RepositoryImpl
import com.example.cinema.model.data_base.DataBase
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO

class FavoriteMovieFragmentViewModel(
    private val liveDataToObserve: MutableLiveData<MovieDTO> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
) : ViewModel() {

    fun getDataFromDataBase(context: Context) {

        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)

        try {
            var favorite_movie = dbHelper.readFavoriteMovieMovieDTO()
            dbHelper.close()
            favorite_movie?.let {
                liveDataToObserve.postValue(it)
            }
        } catch (e: NullPointerException) {
        }


    }

    fun getData(): MutableLiveData<MovieDTO> {
        return liveDataToObserve
    }

    fun changeLikeDataInDB(like: Boolean, aboutMovieItem: Docs, context: Context) {
        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)
        if (like) {
            dbHelper.addFavoriteMovie(aboutMovieItem)

        } else {
            dbHelper.removeFavoriteMovie(aboutMovieItem)
        }

        try {
            var favorite_movie = dbHelper.readFavoriteMovieMovieDTO()
            dbHelper.close()
            favorite_movie?.let {
                repositoryImpl.setFavoriteMovie(it)
                liveDataToObserve.postValue(it)
            }
        } catch (e: NullPointerException) {
        }

    }
    fun getWatched(aboutMovieItem: Docs, context: Context): Boolean {
        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)
        val watched = dbHelper.watched(aboutMovieItem)
        dbHelper.close()
        return watched
    }


}