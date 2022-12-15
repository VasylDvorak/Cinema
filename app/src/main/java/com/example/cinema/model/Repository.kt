package com.example.cinema.model

interface Repository {
    fun getAboutMovieFromServer(): AboutMovie
    fun getAboutMovieLocalStorage(): AboutMovie
}