package com.example.cinema.app

import com.example.cinema.model.serch_name_movie_model.MovieDTO

sealed class AppState {
    data class Success(val AboutMovieData: MovieDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
