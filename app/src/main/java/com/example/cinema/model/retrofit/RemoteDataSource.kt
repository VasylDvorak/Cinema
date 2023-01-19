package com.example.cinema.model.retrofit


import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_movie_information.MovieInformation
import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_the_best.TheBestMovie
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

const val url_trailer = "https://www.youtube.com/embed/DlM2CWNTQ84"

class RemoteDataSource {

    private val movieApi = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech/api/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(createOkHttpClient(MovieApiInterceptor()))
        .build().create(KinopoiskApi::class.java)


    fun getMovieDetails(request_movie: String?, callback: Callback<MovieInformation>) {
        movieApi.getMovie(keyword = request_movie!!).enqueue(callback)
    }

    fun getBestMovieDetails(
        genres: Int, yearTo: Int, callback: Callback<TheBestMovie>
    ) {
        movieApi.getBest(genres = genres, yearFrom = yearTo-2, yearTo = yearTo).enqueue(callback)
    }

    fun getPlayMovie(idd: Int): String {
      var str = movieApi.getVideo(idd).body()?.items?.get(0)?.url
        if ((str == null)||(str == "")){ str = url_trailer }
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
