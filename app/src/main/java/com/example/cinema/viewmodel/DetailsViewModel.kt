package com.example.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.model.repository.DetailsRepository
import com.example.cinema.model.repository.DetailsRepositoryImpl
import com.example.cinema.model.repository.RemoteDataSource
import com.example.cinema.model.utils.convertDtoToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getLiveData() = detailsLiveData
    fun getMovieFromRemoteSource(request_movie: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(request_movie, callBack)
    }

    private val callBack = object :
        Callback<MovieDTO> {
        override fun onResponse(
            call: Call<MovieDTO>, response:
            Response<MovieDTO>
        ) {

            val serverResponse: MovieDTO? = response.body()

            detailsLiveData.postValue(

                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )


        }

        override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
            detailsLiveData.postValue(
                AppState.Error(
                    Throwable(
                        t.message ?: REQUEST_ERROR
                    )
                )
            )
        }

        private fun checkResponse(serverResponse: MovieDTO): AppState {

            return if (serverResponse == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(serverResponse)
            }
        }
    }
}
