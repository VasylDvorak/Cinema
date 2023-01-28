package com.example.cinema.model.retrofit


import com.example.cinema.model.best_movie_model.MovieDTOBest
import com.example.cinema.model.details_movie_model.MovieDetails
import com.example.cinema.model.serch_name_movie_model.MovieDTO

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class RemoteDataSource {
    companion object {
        var for_adult_setting = false
    }

    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(createOkHttpClient(MovieApiInterceptor()))
        .build().create(MovieAPI::class.java)

    fun getMovieDetails(request_movie: String?, callback: Callback<MovieDTO>) {
        movieApi.getMovie(request_movie).enqueue(callback)
    }

    fun getBestMovieDetails(
        request_type: Int, callback: Callback<MovieDTOBest>
    ) {
        movieApi.getBestMovie(request_type).enqueue(callback)
    }
    fun getPlayMovie(idd: Int, callback: Callback<MovieDetails>){
        movieApi.getMovieDetails(idd).enqueue(callback)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }


    inner class MovieApiInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}
