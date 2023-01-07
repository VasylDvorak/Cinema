package com.example.cinema.model

import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.model.gson_kinopoisk_API.nowPlaying

class RepositoryImpl : Repository {
    private var newest_movie_DTO: MovieDTO = nowPlaying()
    private lateinit var  new_favorite_movie_DTO_changed : MovieDTO
    override fun getAboutMovieFromServer(new_movie_DTO: MovieDTO) {
        newest_movie_DTO = new_movie_DTO
    }

    override fun getAboutMovieLocalStorageNowPlaying(): MovieDTO = newest_movie_DTO
    override fun getAboutMovieLocalStorageUpcoming(): MovieDTO = newest_movie_DTO
    override fun setFavoriteMovie(new_favorite_movie_DTO: MovieDTO) {
        new_favorite_movie_DTO_changed=new_favorite_movie_DTO

    }
    override fun getFavoriteMovie(): MovieDTO=new_favorite_movie_DTO_changed
}