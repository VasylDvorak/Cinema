package com.example.cinema.model


import com.example.cinema.model.model_stuio.MovieDTO

interface Repository {
    fun getAboutMovieFromServer(new_movie_DTO: MovieDTO)
    fun getAboutMovieLocalStorageNowPlaying(): MovieDTO
    fun getAboutMovieLocalStorageUpcoming(): MovieDTO
}