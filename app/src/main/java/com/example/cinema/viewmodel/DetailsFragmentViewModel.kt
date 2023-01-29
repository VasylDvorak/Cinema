package com.example.cinema.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.app.App
import com.example.cinema.app.AppState
import com.example.cinema.model.details_movie_model.MovieDetails
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.retrofit.DetailsRepository
import com.example.cinema.model.retrofit.DetailsRepositoryImpl
import com.example.cinema.model.retrofit.RemoteDataSource
import com.example.cinema.model.room_data_base.LocalRepositoryImpl
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsFragmentViewModel(
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepositoryImpl: LocalRepositoryImpl
    = LocalRepositoryImpl(App.getHistoryDao())
) : ViewModel() {
    private val selected: MutableLiveData<Docs> = MutableLiveData<Docs>()
    private var pr_docs = Docs()

    private fun urlTrailer(docs: Docs) {
        selected.value = docs
    }

    fun getSelected(): LiveData<Docs> {
        return selected
    }

    fun addNowPlaying(docsData: Docs) {
        historyRepositoryImpl.addNowPlayingMovieDTO(docsData)
    }


    fun select(docs: Docs) {
        pr_docs=docs
        detailsRepositoryImpl.getPlayMovieDetails(docs.id, callBack)
    }

    private val callBack = object :
        Callback<MovieDetails> {
        @SuppressLint("SuspiciousIndentation")
        override fun onResponse(
            call: Call<MovieDetails>, response:
            Response<MovieDetails>
        ) {

            val serverResponse: MovieDetails? =
                response.body()

                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
        }


        override fun onFailure(
            call: Call<MovieDetails>,
            t: Throwable
        ) {
                AppState.Error(Throwable(t.message ?: REQUEST_ERROR))
        }

        private fun checkResponse(serverResponse: MovieDetails)
                : AppState {
            return if (serverResponse == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {

                try{
                    pr_docs.url_trailer = serverResponse.videos.trailers[0].url
                }
                catch (e: IndexOutOfBoundsException){}

                try{
                    pr_docs.country=serverResponse.countries[0].name
                }
                catch (e: IndexOutOfBoundsException){}

                urlTrailer(pr_docs)
                AppState.Success(MovieDTO())
            }
        }
    }
}