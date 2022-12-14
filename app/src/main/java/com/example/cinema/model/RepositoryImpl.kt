package com.example.cinema.model

class RepositoryImpl : Repository {
    override fun getAboutMovieFromServer(): AboutMovie {
        return AboutMovie()
    }

    override fun getAboutMovieLocalStorageNowPlaying(): List<AboutMovie> {
        return nowPlaying()
    }

    override fun getAboutMovieLocalStorageUpcoming(): List<AboutMovie> {
        return upcoming()
    }


}