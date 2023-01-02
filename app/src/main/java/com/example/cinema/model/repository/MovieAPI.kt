package com.example.cinema.model.repository


import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieAPI {
    @GET("movie?field=name")
    fun getMovie(

        @Query("search") request_movie: String,
        @Query("isStrict") strict: Boolean,
        @Query("token") api_key: String,

        ): Call<MovieDTO>


}

/*
Форма запроса
"https://api.kinopoisk.dev/movie?field" +
"=name&search=${find_request}&isStrict=false&" +
"token=${BuildConfig.KINOPOISK_API_KEY}"
*/