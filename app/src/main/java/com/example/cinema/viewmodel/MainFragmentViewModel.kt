package com.example.cinema.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.R
import com.example.cinema.app.App
import com.example.cinema.app.AppState
import com.example.cinema.appliction_repository.Repository
import com.example.cinema.appliction_repository.RepositoryImpl
import com.example.cinema.model.serch_name_movie_model.Docs

import com.example.cinema.model.serch_name_movie_model.MovieDTO

import com.example.cinema.model.retrofit.DetailsRepository
import com.example.cinema.model.retrofit.DetailsRepositoryImpl
import com.example.cinema.model.retrofit.RemoteDataSource
import com.example.cinema.model.room_data_base.LocalRepositoryImpl
import com.example.cinema.view.MainActivity.Companion.start_cinema
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MainFragmentViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDao())

) : ViewModel() {

    fun liveDataToObserveUpdate() {
        Thread {
            liveDataToObserve.postValue(
                AppState.Success(
                    repositoryImpl.getAboutMovieLocalStorageUpcoming()
                )
            )
        }.start()
    }


    fun getFromDataBase() {
        if (start_cinema == ""){
        try {
            Thread{
            var start_condition =historyRepositoryImpl.readCurrentMovieDTO()
            start_condition?.let {
                if (it.docs.size > 0) {
                    repositoryImpl.setAboutMovieFromServer(start_condition)
                    liveDataToObserveUpdate()
                }else{
                    startSearch(App.appInstance!!.applicationContext.resources.
                    getString(R.string.first_request))
                }
            }
            }.start()
        } catch (e: NullPointerException) {
            startSearch(App.appInstance!!.applicationContext
                .resources.getString(R.string.first_request))
        }
        }else{
            startSearch(start_cinema)
            start_cinema = ""
       }
    }

    fun startSearch(start : String){
        getDataFromRemoteSource(start)
    }


  private var liveDataToObserveNowPlaying: MutableLiveData<MovieDTO> = MutableLiveData()
    fun getNowPlayingFromDataBase() {
           try {
               Thread{
            var start_condition = historyRepositoryImpl.readNowPlayingMovieMovieDTO()
            start_condition?.let {
                    repositoryImpl.setTheNowPlayingMovie(start_condition)
                    liveDataToObserveNowPlaying.postValue(repositoryImpl.getTheNowPlayingMovie())
            }}.start()
        } catch (e: NullPointerException) {
        }
    }

    fun getDataNowPlaying(): MutableLiveData<MovieDTO> {
        return liveDataToObserveNowPlaying
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
    fun getDataFromRemoteSource(request_movie: String?) {
        start_cinema=""
        try {
            liveDataToObserve.value = AppState.Loading
        }catch (e: IllegalStateException){}
        detailsRepositoryImpl.getMovieDetailsFromServer(request_movie, callBack)
    }

    private val callBack = object :
        Callback<MovieDTO> {
        override fun onResponse(
            call: Call<MovieDTO>, response:
            Response<MovieDTO>
        ) {
            val serverResponse: MovieDTO? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
            liveDataToObserve.postValue(
                AppState.Error(
                    Throwable(
                        t.message ?: REQUEST_ERROR
                    )
                )
            )
        }

        private fun checkResponse(serverResponse: MovieDTO): AppState {

            return if (serverResponse == null) {
                return  AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                historyRepositoryImpl.renewCurrentMovieDTO(serverResponse)
                repositoryImpl.setAboutMovieFromServer(serverResponse)
              return  AppState.Success(serverResponse)
            }
        }
    }
}