package com.example.cinema.viewmodel

import com.example.cinema.model.gson_kinopoisk_API.MovieDTO


sealed class AppState {
    data class Success(val AboutMovieData: MovieDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
