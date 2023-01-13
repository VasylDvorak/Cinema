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

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"


class TheBestMovieViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    private lateinit var context: Context
    fun liveDataToObserveUpdate() {
        Thread {
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getTheBestMovie()))
        }.start()
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


    fun getData() = liveDataToObserve
    fun getDataFromRemoteSource(type: Int, context: Context?) {
        this.context = context!!
        liveDataToObserve.value = AppState.Loading
        detailsRepositoryImpl.getBestMovieDetailsFromServer(type.toString(), callBack)
    }

    private val callBack = object :
        Callback<com.example.cinema.model.best_movie_model.MovieDTO> {
        override fun onResponse(
            call: Call<com.example.cinema.model.best_movie_model.MovieDTO>, response:
            Response<com.example.cinema.model.best_movie_model.MovieDTO>
        ) {

            val serverResponse: com.example.cinema.model.best_movie_model.MovieDTO? =
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
            call: Call<com.example.cinema.model.best_movie_model.MovieDTO>,
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

        private fun checkResponse(serverResponse: com.example.cinema.model.best_movie_model.MovieDTO)
                : AppState {
            return if (serverResponse == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                var dbHelper = DataBase(context, null)
                //  var dbHelper = DataBaseRoom(context)

                var converted_response = converter(serverResponse)
                dbHelper.renewCurrentMovieDTO(converted_response)
                dbHelper.close()

                AppState.Success(converted_response)
            }
        }


    }


    fun converter(serverResponse: com.example.cinema.model.best_movie_model.MovieDTO): MovieDTO {
        var movieDTO = MovieDTO()

        for (docss in serverResponse.docs) {
            var docs_model_studio = Docs()


            docs_model_studio.apply {

                id = docss.id ?: 0
                movieLength = docss.movieLength ?: 0
                description = docss.description ?: ""
                enName = docss.enName ?: ""
                alternativeName = docss.alternativeName ?: ""
                name = docss.name ?: ""
                try {
                    poster.url = docss.poster.url
                } catch (e: NullPointerException) {
                }
                rating.kp = docss.rating.kp ?: 0.0
                rating.filmCritics = docss.rating.filmCritics  ?: 0.0
                rating.russianFilmCritics = docss.rating.russianFilmCritics  ?: 0
                year = docss.year ?: 0
                type = docss.type ?: ""
                rating.await = docss.rating.await.toInt() ?: 0
                alternativeName = docss.alternativeName ?: ""
                movieDTO.docs.add(this)
            }


        }

        return movieDTO
    }


}




