package com.example.cinema.viewmodel

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.cinema.BuildConfig
import com.example.cinema.R
import com.example.cinema.model.DetailsService
import com.example.cinema.model.REQUEST_MOVIE
import com.example.cinema.model.Repository
import com.example.cinema.model.RepositoryImpl
import com.example.cinema.model.data_base.DataBase
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.view.Extensions


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

class MainFragmentViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
) : ViewModel() {

    fun getDataFromLocalSource(context: Context?) {
        liveDataToObserve.value = AppState.Loading
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        liveDataToObserveUpdate()
    }

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


    fun getDataFromRemoteSource(
        find_request: String?,
        context: Context?
    ) {


        LocalBroadcastManager.getInstance(context!!)
            .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        context.startService(Intent(context, DetailsService::class.java).apply {
            putExtra(
                REQUEST_MOVIE, "https://api.kinopoisk.dev/movie?limit=10&field" +
                        "=name&search=${find_request}&isStrict=false&" +
                        "token=${BuildConfig.KINOPOISK_API_KEY}"
            )
        })

    }

    fun getData(): MutableLiveData<AppState> {
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
        dbHelper.close()

    }

    fun getLike(aboutMovieItem: Docs, context: Context): Boolean {
        var dbHelper = DataBase(context, null)
        //  var dbHelper = DataBaseRoom(context)
        val like_movie = dbHelper.like(aboutMovieItem)
        dbHelper.close()
        return like_movie

    }


    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("Range")
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("TAG", "onReceive")

            val strExtra = intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)

            when (strExtra) {
                DETAILS_INTENT_EMPTY_EXTRA -> Extensions.showToast(
                    context, context.resources.getString(R.string.error_reading)
                )
                DETAILS_DATA_EMPTY_EXTRA -> Extensions.showToast(
                    context, context.resources.getString(R.string.error_reading)
                )
                DETAILS_RESPONSE_EMPTY_EXTRA -> Extensions.showToast(
                    context, context.resources.getString(R.string.error_reading)
                )
                DETAILS_REQUEST_ERROR_EXTRA -> Extensions.showToast(
                    context, context.resources.getString(R.string.error_reading)
                )
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> Extensions.showToast(
                    context, context.resources.getString(R.string.error_reading)
                )
                DETAILS_URL_MALFORMED_EXTRA -> Extensions.showToast(
                    context, context.resources.getString(R.string.error_reading)
                )

                DETAILS_RESPONSE_SUCCESS_EXTRA -> {
                    var movieDTO_from_broadcast =
                        intent.getParcelableExtra<MovieDTO>(DETAILS_CONDITION_EXTRA)

                    var dbHelper = DataBase(context, null)
                    //  var dbHelper = DataBaseRoom(context)

                    dbHelper.renewCurrentMovieDTO(movieDTO_from_broadcast!!)
                    dbHelper.close()
                    movieDTO_from_broadcast.let {
                        repositoryImpl.setAboutMovieFromServer(movieDTO_from_broadcast)

                        getDataFromLocalSource(context)
                    }


                }
            }
        }
    }


}