package com.example.cinema.model

import com.example.cinema.model.gson_kinopoisk_API.MovieDTO

interface Repository {
    fun setAboutMovieFromServer(new_movie_DTO: MovieDTO)
    fun getAboutMovieLocalStorageNowPlaying(): MovieDTO
    fun getAboutMovieLocalStorageUpcoming(): MovieDTO
    fun setFavoriteMovie(new_favorite_movie_DTO: MovieDTO)
    fun getFavoriteMovie(): MovieDTO
    fun setTheBestMovie(the_best_movie_DTO: MovieDTO)
    fun getTheBestMovie(): MovieDTO

}