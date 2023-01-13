package com.example.cinema.model.retrofit_repository


import android.content.Context
import com.example.cinema.model.model_stuio.MovieDTO


interface DetailsRepository {
    fun getMovieDetailsFromServer(
        request_movie: String?,
        callback: retrofit2.Callback<MovieDTO>
    )

    fun getBestMovieDetailsFromServer(
        find_type: String,
        callback: retrofit2.Callback<com.example.cinema.model.best_movie_model.MovieDTO>
    )

    fun getPlayMovieDetails(
        idd: String,
        context: Context
    ): String

}
