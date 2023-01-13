package com.example.cinema.model

import com.example.cinema.model.model_stuio.MovieDTO

interface Repository {
    fun setAboutMovieFromServer(new_movie_DTO: MovieDTO)

    // fun getAboutMovieLocalStorageNowPlaying(): MovieDTO
    fun getAboutMovieLocalStorageUpcoming(): MovieDTO
    fun setFavoriteMovie(new_favorite_movie_DTO: MovieDTO)
    fun getFavoriteMovie(): MovieDTO
    fun setTheBestMovie(the_best_movie_DTO: MovieDTO)
    fun getTheBestMovie(): MovieDTO
    fun setTheNowPlayingMovie(the_now_playing_movie_DTO: MovieDTO)
    fun getTheNowPlayingMovie(): MovieDTO

}