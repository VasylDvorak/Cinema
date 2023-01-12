package com.example.cinema.model

import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.model.gson_kinopoisk_API.nowPlaying
import com.example.cinema.model.gson_kinopoisk_API.upcoming

class RepositoryImpl : Repository {
    private var newest_movie_DTO: MovieDTO = upcoming()
    private lateinit var  new_favorite_movie_DTO_changed : MovieDTO
    private lateinit var  the_best_movie_DTO_changed : MovieDTO
    private var  the_now_playing_movie_DTO_changed : MovieDTO = nowPlaying()

    override fun setAboutMovieFromServer(new_movie_DTO: MovieDTO) {
        newest_movie_DTO = new_movie_DTO
    }

   // override fun getAboutMovieLocalStorageNowPlaying(): MovieDTO = the_now_playing_movie_DTO_changed
    override fun getAboutMovieLocalStorageUpcoming(): MovieDTO = newest_movie_DTO
    override fun setFavoriteMovie(new_favorite_movie_DTO: MovieDTO) {
        new_favorite_movie_DTO_changed=new_favorite_movie_DTO

    }
    override fun getFavoriteMovie(): MovieDTO=new_favorite_movie_DTO_changed
    override fun setTheBestMovie(the_best_movie_DTO: MovieDTO) {
        the_best_movie_DTO_changed=the_best_movie_DTO
    }

    override fun getTheBestMovie(): MovieDTO = the_best_movie_DTO_changed
    override fun setTheNowPlayingMovie(the_now_playing_movie_DTO: MovieDTO) {
        the_now_playing_movie_DTO_changed=the_now_playing_movie_DTO
    }

    override fun getTheNowPlayingMovie(): MovieDTO = the_now_playing_movie_DTO_changed

}