package com.example.cinema.model.repository


import com.example.cinema.model.gson_kinopoisk_API.MovieDTO


interface DetailsRepository {
    fun getMovieDetailsFromServer(
        request_movie: String,
        callback: retrofit2.Callback<MovieDTO>
    )
}
