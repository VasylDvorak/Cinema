package com.example.cinema.appliction_repository

import com.example.cinema.model.serch_name_movie_model.MovieDTO

interface Repository {

    fun setAboutMovieFromServer(new_movie_DTO: MovieDTO)
    fun getAboutMovieLocalStorageUpcoming(): MovieDTO
    fun setFavoriteMovie(new_favorite_movie_DTO: MovieDTO)
    fun getFavoriteMovie(): MovieDTO
    fun setTheBestMovie(the_best_movie_DTO: MovieDTO)
    fun getTheBestMovie(): MovieDTO
    fun setTheNowPlayingMovie(the_now_playing_movie_DTO: MovieDTO)
    fun getTheNowPlayingMovie(): MovieDTO

}