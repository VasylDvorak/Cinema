package com.example.cinema.model.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import com.example.cinema.BuildConfig
import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_movie_information.MovieInformation
import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_the_best.TheBestMovie
import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_video.VideoForMovie
import retrofit2.Call
import retrofit2.http.Path


interface KinopoiskApi {
    @Headers("X-API-KEY: ${BuildConfig.KINOPOISK_API_KEY}")
    @GET("v2.1/films/search-by-keyword")
    fun getMovie(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1
    ): Call<MovieInformation>

    @Headers("X-API-KEY: ${BuildConfig.KINOPOISK_API_KEY}")
    @GET("v2.2/films/{id}/videos")
    fun getVideo(
        @Path("id") id : Int
    ): Response<VideoForMovie>


    @Headers("X-API-KEY: ${BuildConfig.KINOPOISK_API_KEY}")
    @GET("v2.2/films")
    fun getBest(
        @Query("genres") genres: Int = 1,
        @Query("order") order: String = "YEAR",
        @Query("type") type: String = "ALL",
        @Query("ratingFrom") ratingFrom: Int = 0,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 2018,
        @Query("yearTo") yearTo: Int = 2023,
        @Query("page") page: Int = 1
    ): Call<TheBestMovie>

}