package com.example.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.app.App
import com.example.cinema.app.AppState
import com.example.cinema.appliction_repository.Repository
import com.example.cinema.appliction_repository.RepositoryImpl
import com.example.cinema.model.best_movie_model.MovieDTOBest

import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.retrofit.DetailsRepository
import com.example.cinema.model.retrofit.DetailsRepositoryImpl
import com.example.cinema.model.retrofit.RemoteDataSource
import com.example.cinema.model.room_data_base.LocalRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"


class TheBestMovieViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDao())
) : ViewModel() {

    fun liveDataToObserveUpdate() {
        Thread {
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getTheBestMovie()))
        }.start()
    }

    fun changeLikeDataInDB(like: Boolean, aboutMovieItem: Docs) {

        if (like) {
            historyRepositoryImpl.addFavoriteMovie(aboutMovieItem)

        } else {
            historyRepositoryImpl.removeFavoriteMovie(aboutMovieItem)
        }
    }

    fun getLike(aboutMovieItem: Docs): Boolean {

        val like_movie = historyRepositoryImpl.like(aboutMovieItem)
        return like_movie
    }

    fun getWatched(aboutMovieItem: Docs): Boolean {

        val watched = historyRepositoryImpl.watched(aboutMovieItem)

        return watched
    }


    fun getData() = liveDataToObserve
    fun getDataFromRemoteSource(type: Int) {
        liveDataToObserve.value = AppState.Loading
        detailsRepositoryImpl.getBestMovieDetailsFromServer(type, callBack)
    }

    private val callBack = object :
        Callback<MovieDTOBest> {
        override fun onResponse(
            call: Call<MovieDTOBest>, response:
            Response<MovieDTOBest>
        ) {

            val serverResponse: MovieDTOBest? =
                response.body()
            liveDataToObserve.postValue(

                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(
            call: Call<MovieDTOBest>,
            t: Throwable
        ) {

            liveDataToObserve.postValue(
                AppState.Error(
                    Throwable(
                        t.message ?: REQUEST_ERROR
                    )
                )
            )
        }

        private fun checkResponse(serverResponse: MovieDTOBest)
                : AppState {
            return if (serverResponse == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {

                var converted_response = detailsRepositoryImpl.converter(serverResponse)

                AppState.Success(converted_response)
            }
        }
    }
}




