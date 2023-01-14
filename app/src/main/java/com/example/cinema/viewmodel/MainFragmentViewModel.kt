package com.example.cinema.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.Repository
import com.example.cinema.model.RepositoryImpl
import com.example.cinema.model.data_base.DataBase
import com.example.cinema.model.model_stuio.Docs

import com.example.cinema.model.model_stuio.MovieDTO

import com.example.cinema.model.retrofit_repository.DetailsRepository
import com.example.cinema.model.retrofit_repository.DetailsRepositoryImpl
import com.example.cinema.model.retrofit_repository.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_CONDITION_EXTRA = "NEW REQUEST"
const val PROCESS_ERROR = "Обработка ошибки"
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MainFragmentViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    private lateinit var context : Context

    fun liveDataToObserveUpdate() {
        Thread {

            liveDataToObserve.postValue(
                AppState.Success(
                    repositoryImpl.getAboutMovieLocalStorageUpcoming()
                )
            )
        }.start()


    }


    fun getFromDataBase(context: Context) {
        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)
        try {
            var start_condition = dbHelper.readCurrentMovieDTO()
            dbHelper.close()
            start_condition?.let {
                if (it.docs.size > 0) {
                    repositoryImpl.setAboutMovieFromServer(start_condition)

                    liveDataToObserveUpdate()

                }
            }

        } catch (e: NullPointerException) {
        }



    }
  private var liveDataToObserveNowPlaying: MutableLiveData<MovieDTO> = MutableLiveData()
    fun getNowPlayingFromDataBase(context: Context) {
        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)
        try {
            var start_condition = dbHelper.readNowPlayingMovieMovieDTO()

            dbHelper.close()
            start_condition?.let {
                if (!(it.equals(""))) {
                    repositoryImpl.setTheNowPlayingMovie(start_condition)

                    liveDataToObserveNowPlaying.postValue(repositoryImpl.getTheNowPlayingMovie())

                }
            }

        } catch (e: NullPointerException) {
        }
    }

    fun getDataNowPlaying(): MutableLiveData<MovieDTO> {
        return liveDataToObserveNowPlaying
    }

    fun changeLikeDataInDB(like: Boolean, aboutMovieItem: Docs, context: Context) {
        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)
        if (like) {
            dbHelper.addFavoriteMovie(aboutMovieItem)

        } else {
            dbHelper.removeFavoriteMovie(aboutMovieItem)
        }
        dbHelper.close()

    }

    fun getLike(aboutMovieItem: Docs, context: Context): Boolean {
        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)
        val like_movie = dbHelper.like(aboutMovieItem)
        dbHelper.close()
        return like_movie

    }

    fun getWatched(aboutMovieItem: Docs, context: Context): Boolean {
        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)
        val watched = dbHelper.watched(aboutMovieItem)
        dbHelper.close()
        return watched
    }

    fun getData() = liveDataToObserve
    fun getDataFromRemoteSource(request_movie: String?, context: Context?) {
        this.context=context!!
        liveDataToObserve.value = AppState.Loading
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
                var dbHelper = DataBase(context, null)
                //  var dbHelper = DataBaseRoom(context)

                dbHelper.renewCurrentMovieDTO(serverResponse)
                dbHelper.close()

                repositoryImpl.setAboutMovieFromServer(serverResponse)

              return  AppState.Success(serverResponse)
            }
        }
    }











}