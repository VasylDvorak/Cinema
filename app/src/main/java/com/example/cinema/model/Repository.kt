package com.example.cinema.model

import com.example.cinema.model.gson_kinopoisk_API.MovieDTO

interface Repository {
    fun getAboutMovieFromServer(new_movie_DTO: MovieDTO)
    fun getAboutMovieLocalStorageNowPlaying(): MovieDTO
    fun getAboutMovieLocalStorageUpcoming(): MovieDTO
    fun setFavoriteMovie(new_favorite_movie_DTO: MovieDTO)
   fun getFavoriteMovie():MovieDTO
}