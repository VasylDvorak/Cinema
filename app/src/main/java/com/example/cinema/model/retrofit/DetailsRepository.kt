package com.example.cinema.model.retrofit


import com.example.cinema.model.best_movie_model.MovieDTOBest
import com.example.cinema.model.details_movie_model.MovieDetails
import com.example.cinema.model.serch_name_movie_model.MovieDTO


interface DetailsRepository {
    fun getMovieDetailsFromServer(request_movie: String?, callback: retrofit2.Callback<MovieDTO>)

    fun getBestMovieDetailsFromServer(find_type: Int, callback: retrofit2.Callback<MovieDTOBest>)

    fun getPlayMovieDetails(idd: Int, callback: retrofit2.Callback<MovieDetails>)

    fun converter(input: MovieDTOBest): MovieDTO
}
