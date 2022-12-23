package com.example.cinema.model

import com.example.cinema.model.gson_decoder.MovieDTO
import com.example.cinema.model.gson_decoder.nowPlaying

class RepositoryImpl : Repository {
    private var newest_movie_DTO: MovieDTO = nowPlaying()
    override fun getAboutMovieFromServer(new_movie_DTO: MovieDTO) {
        newest_movie_DTO = new_movie_DTO
    }

    override fun getAboutMovieLocalStorageNowPlaying(): MovieDTO = newest_movie_DTO
    override fun getAboutMovieLocalStorageUpcoming(): MovieDTO = newest_movie_DTO
}