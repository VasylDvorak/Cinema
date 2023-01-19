package com.example.cinema.model.retrofit.new_API


import com.example.cinema.model.best_movie_model.MovieDTOBest
import com.example.cinema.model.retrofit.new_API.new_model_movie_information.MovieInformation
import com.example.cinema.model.retrofit.new_API.new_model_the_best.Genre
import com.example.cinema.model.retrofit.new_API.new_model_the_best.TheBestMovie
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
    fun getPlayMovieDetails(idd: String): String
    fun fromMovieInformationToMovieDTO (input : MovieInformation) : MovieDTO
    fun fromTheBestMovieToMovieDTO(input: TheBestMovie): MovieDTO
}
