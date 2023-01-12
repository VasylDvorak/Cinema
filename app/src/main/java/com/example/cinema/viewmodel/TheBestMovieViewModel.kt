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


class TheBestMovieViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
) : ViewModel() {

    private lateinit var context_VM: Context

    fun getAboutMovie() = getDataFromLocalSource()
    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        context_VM.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        liveDataToObserveUpdate()
    }

    fun liveDataToObserveUpdate() {
        Thread {
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getTheBestMovie()))
        }.start()
    }


    fun getDataFromRemoteSource(
        find_type: Int?, context: Context
    ) {

        context_VM = context
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        context.startService(Intent(context, DetailsService::class.java).apply {
            putExtra(
                REQUEST_MOVIE,
                "https://api.kinopoisk.dev/movie?limit=10&field=rating.kp&search=1-10"
                        + "&field=typeNumber&search=${find_type}&"
                        + "sortField=rating.kp&sortType=-1&token=${BuildConfig.KINOPOISK_API_KEY}"
            )
        })


    }

    fun getData(): MutableLiveData<AppState> {
        return liveDataToObserve
    }

    fun changeLikeDataInDB(like: Boolean, aboutMovieItem: Docs, context: Context) {
        var dbHelper = DataBase(context, null)
        if (like) {
            dbHelper.addFavoriteMovie(aboutMovieItem)

        } else {
            dbHelper.removeFavoriteMovie(aboutMovieItem)
        }
        dbHelper.close()

    }

    fun getLike(aboutMovieItem: Docs, context: Context): Boolean {
        var dbHelper = DataBase(context, null)
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
                    movieDTO_from_broadcast?.let {
                        repositoryImpl.setTheBestMovie(movieDTO_from_broadcast)
                        getDataFromLocalSource()
                    }


                }
            }
        }
    }


}