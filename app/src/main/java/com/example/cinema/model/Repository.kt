package com.example.cinema.model

import com.example.cinema.model.gson_decoder.MovieDTO

interface Repository {
    fun getAboutMovieFromServer(): MovieDTO
    fun getAboutMovieLocalStorageNowPlaying(): List<MovieDTO>
    fun getAboutMovieLocalStorageUpcoming(): List<MovieDTO>
}