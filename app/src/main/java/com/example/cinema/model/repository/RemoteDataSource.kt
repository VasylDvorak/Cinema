package com.example.cinema.model.repository


import com.example.cinema.BuildConfig
import com.example.cinema.model.model_stuio.MovieDTO
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RemoteDataSource {

    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(createOkHttpClient(MovieApiInterceptor()))
        .build().create(MovieAPI::class.java)

    fun getMovieDetails(request_movie: String, callback: Callback<MovieDTO>) {
        movieApi.getMovie(request_movie, false, BuildConfig.KINOPOISK_API_KEY).enqueue(callback)
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
