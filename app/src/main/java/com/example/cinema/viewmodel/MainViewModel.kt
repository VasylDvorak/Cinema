package com.example.cinema.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.cinema.R
import com.example.cinema.model.DetailsService
import com.example.cinema.model.REQUEST_MOVIE
import com.example.cinema.model.Repository
import com.example.cinema.model.RepositoryImpl

import com.example.cinema.model.model_stuio.MovieDTO
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

class MainViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
) : ViewModel() {

    private lateinit var context_VM: Context


    var liveDataCurrent = MutableLiveData<AppState>()
    fun getAboutMovie() = getDataFromLocalSource(true)
    fun getUpcomingMovie() = repositoryImpl.getAboutMovieLocalStorageUpcoming()

    private fun getDataFromLocalSource(isNowPlaying: Boolean) {
        liveDataToObserve.value = AppState.Loading
        context_VM.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
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

    fun getDataFromRemoteSource(
        find_request: String?,
        context: Context?
    ) {

        context_VM = context!!

        LocalBroadcastManager.getInstance(context)
            .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        context.startService(Intent(context, DetailsService::class.java).apply {
            putExtra(REQUEST_MOVIE, find_request)
        })


    }

    fun getData(): MutableLiveData<AppState> {
        return liveDataToObserve
    }


    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
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
                        repositoryImpl.getAboutMovieFromServer(movieDTO_from_broadcast)
                        getDataFromLocalSource(true)
                    }


                }
            }
        }
    }


}