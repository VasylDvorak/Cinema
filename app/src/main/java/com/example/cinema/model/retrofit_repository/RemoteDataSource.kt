package com.example.cinema.model.retrofit_repository


import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cinema.BuildConfig
import com.example.cinema.model.model_stuio.MovieDTO

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

const val url_trailer = "https://www.youtube.com/embed/DlM2CWNTQ84"

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

    fun getMovieDetails(request_movie: String?, callback: Callback<MovieDTO>) {
        movieApi.getMovie(request_movie).enqueue(callback)
    }

    fun getBestMovieDetails(
        request_type: String, callback: Callback<com.example.cinema.model.
        best_movie_model.MovieDTO>
    ) {
        movieApi.getBestMovie(request_type).enqueue(callback)
    }

    fun getPlayMovie(idd: String, context: Context): String {
        var trailerUrl = "https://api.kinopoisk.dev/movie?field=id&search=${idd}&token" +
                "=${BuildConfig.KINOPOISK_API_KEY}"
        var str = url_trailer
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            trailerUrl,
            { result ->
                run {

                    try {
                        str = (JSONObject(result).getJSONObject("videos")
                            .getJSONArray("trailers")[0] as JSONObject)
                            .getString("url")

                    } catch (e: JSONException) {
                    }
                }
            },
            { error ->
                run {

                }
            }

        )
        queue.add(request)

        return str
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
