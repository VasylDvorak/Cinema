package com.example.cinema.model.retrofit_repository


import com.example.cinema.BuildConfig
import com.example.cinema.model.model_stuio.MovieDTO
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

    ): Call<com.example.cinema.model.best_movie_model.MovieDTO>


}

/*

"https://api.kinopoisk.dev/movie?limit=10&field=rating.kp&search=1-10"
+ "&field=typeNumber&search=${find_type}&"
+ "sortField=rating.kp&sortType=-1&token=${BuildConfig.KINOPOISK_API_KEY}"

*/





/*
Форма запроса
"https://api.kinopoisk.dev/movie?field" +
"=name&search=${find_request}&isStrict=false&" +
"token=${BuildConfig.KINOPOISK_API_KEY}"
*/