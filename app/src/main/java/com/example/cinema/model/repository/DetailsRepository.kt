package com.example.cinema.model.repository


import com.example.cinema.model.model_stuio.MovieDTO


interface DetailsRepository {
    fun getMovieDetailsFromServer(
        request_movie: String,
        callback: retrofit2.Callback<MovieDTO>
    )
}
