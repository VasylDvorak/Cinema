package com.example.cinema.viewmodel

import com.example.cinema.model.AboutMovie


sealed class AppState {
    data class Success(val AboutMovieData : AboutMovie) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}
