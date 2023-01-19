package com.example.cinema.model.retrofit


import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_movie_information.MovieInformation
import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_the_best.TheBestMovie
import com.example.cinema.model.serch_name_movie_model.MovieDTO


interface DetailsRepository {
    fun getMovieDetailsFromServer(
        request_movie: String?,
        callback: retrofit2.Callback<MovieInformation>
    )
    fun getBestMovieDetailsFromServer(
        genre: String, yearTo : Int,
        callback: retrofit2.Callback<TheBestMovie>
    )
    fun getPlayMovieDetails(idd: Int): String
    fun fromMovieInformationToMovieDTO (input : MovieInformation) : MovieDTO
    fun fromTheBestMovieToMovieDTO(input: TheBestMovie): MovieDTO
}
