package com.example.cinema.model

class RepositoryImpl : Repository {
    override fun getAboutMovieFromServer(): AboutMovie = AboutMovie()
    override fun getAboutMovieLocalStorageNowPlaying(): List<AboutMovie> = nowPlaying()
    override fun getAboutMovieLocalStorageUpcoming(): List<AboutMovie> = upcoming()
}