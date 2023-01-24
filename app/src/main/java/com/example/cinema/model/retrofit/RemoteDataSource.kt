package com.example.cinema.model.retrofit


import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cinema.BuildConfig
import com.example.cinema.R
import com.example.cinema.app.App
import com.example.cinema.model.best_movie_model.MovieDTOBest
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.utils.Extensions
import com.example.cinema.view.details_fragment.DetailsFragment.Companion.can_show

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

    fun getPlayMovie(idd: String): MutableList<String> {
        var answer: MutableList<String> = mutableListOf()
        var trailerUrl = "https://api.kinopoisk.dev/movie?field=id&search=${idd}&token" +
                "=${BuildConfig.KINOPOISK_API_KEY}"
        println(trailerUrl)
        var str = url_trailer
        var country = ""
        var ageRating = 18
        val queue = Volley.newRequestQueue(App.appInstance!!.applicationContext)
        val request = StringRequest(
            Request.Method.GET,
            trailerUrl,
            { result ->
                run {

                    try {
                        str = (JSONObject(result).getJSONObject("videos")
                            .getJSONArray("trailers")[0] as JSONObject)
                            .getString("url")
                        answer.add(str)
                        ageRating = JSONObject(result).getInt("ageRating")
                       country = (JSONObject(result).
                       getJSONArray("countries")[0] as JSONObject).getString("name")
                        answer.add(country)

                    } catch (e: JSONException) {
                    }
                }
            },
            { error ->
                run {
                Extensions.showToast(App.appInstance!!.applicationContext,
                    App.appInstance!!.applicationContext.getString(R.string.upcoming))
                }
            }
        )
        queue.add(request)
        if ((!for_adult_setting)&&(ageRating >= 18)){
            can_show  = false }
        can_show = true
        while(answer.size < 2){}
        return answer
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
