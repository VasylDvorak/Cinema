package com.example.cinema.model

import com.example.cinema.model.gson_decoder.MovieDTO
import com.example.cinema.model.gson_decoder.nowPlaying
import com.example.cinema.model.gson_decoder.upcoming

class RepositoryImpl : Repository {
    override fun getAboutMovieFromServer(): MovieDTO = MovieDTO()
    override fun getAboutMovieLocalStorageNowPlaying(): MovieDTO = nowPlaying()
    override fun getAboutMovieLocalStorageUpcoming(): MovieDTO = upcoming()
}