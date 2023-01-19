package com.example.cinema.model.room_data_base


import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.serch_name_movie_model.MovieDTO

interface LocalRepository {

    fun readNowPlayingMovieMovieDTO(): MovieDTO
    fun addFavoriteMovie(docs: Docs)
    fun like(docs: Docs): Boolean
    fun readFavoriteMovieMovieDTO(): MovieDTO
    fun watched(docs: Docs): Boolean
    fun renewCurrentMovieDTO(movieDTO: MovieDTO)
    fun updateNote(docs: Docs)
    fun readCurrentMovieDTO(): MovieDTO
    fun removeFavoriteMovie(docs: Docs)
    fun getNote(docs: Docs): Docs
    fun saveEntity(docs: Docs, current: Boolean, watched: Boolean, isLike: Boolean)

}
