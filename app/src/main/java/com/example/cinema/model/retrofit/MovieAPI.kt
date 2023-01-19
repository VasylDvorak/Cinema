package com.example.cinema.model.retrofit

import com.example.cinema.BuildConfig
import com.example.cinema.model.best_movie_model.MovieDTOBest
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie?field=name")
    fun getMovie(

        @Query("search") request_movie: String?,
        @Query("isStrict") strict: Boolean  = false,
        @Query("token") api_key: String = "${ BuildConfig.KINOPOISK_API_KEY}"

    ): Call<MovieDTO>

@GET("movie?limit=10&field=rating.kp&search=1-10&field=typeNumber")
fun getBestMovie(

    @Query("search") find_type: String,
    @Query("sortField=rating.kp&sortType") sortType: String = "-1",
    @Query("token") api_key: String = "${ BuildConfig.KINOPOISK_API_KEY}"

    ): Call<MovieDTOBest>
}
