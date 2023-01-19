package com.example.cinema.model.utils

import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_movie_information.MovieInformation
import com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_the_best.TheBestMovie
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.serch_name_movie_model.Poster
import com.example.cinema.model.serch_name_movie_model.Rating
import com.example.cinema.model.room_data_base.HistoryEntity

fun converterMovieInformationToMovieDTO(input : MovieInformation) : MovieDTO{

    var docs = input.films.map { Docs(
        alternativeName = it.nameEn,
        description = it.description,
        id = it.filmId,
        movieLength = it.filmLength,
        name = it.nameRu,
        rating = Rating(russianFilmCritics = it.ratingVoteCount ?: 0,
            kp = 8.8),
        poster = Poster(url = it.posterUrl),
        shortDescription = it.description,
        type = it.type,
        year = 2015,
        current=true,
        genre= it.genres.get(0).genre)
    } as MutableList

   return MovieDTO(docs, input.searchFilmsCountResult, 1, input.pagesCount, input.pagesCount)

}

fun convertBestToMovieDTO(input: TheBestMovie): MovieDTO{

    var docs = input.items.map { Docs(
        alternativeName = it.nameOriginal,
        description = it.nameRu,
        id = it.kinopoiskId,
        movieLength = it.year.toString(),
        name = it.nameRu,
        rating = Rating(russianFilmCritics = it.ratingKinopoisk.toInt(), kp = it.ratingImdb),
        poster = Poster(url = it.posterUrl),
        shortDescription = it.nameOriginal,
        type = it.type,
        year = it.year,
        current=true,
        genre= it.genres.get(0).genre)
    } as MutableList
    return MovieDTO(docs, input.total, 1, input.totalPages, input.totalPages)

}

fun convertHistoryEntityToMovie(entityList: MutableList<HistoryEntity>): MutableList<Docs> {
    return entityList.map {

        Docs(
            alternativeName = it.alternativeName,
            description = it.description,
            id = it.id_server,
            movieLength = it.movieLength.toString(),
            name = it.name,
            rating = Rating(russianFilmCritics = it.rating_russianFilmCritics, kp = it.rating_kp),
            poster = Poster(url = it.poster_url),
            shortDescription = it.shortDescription,
            type = it.type,
            year = it.year,
            current=if (it.current_request == 1) true else false,
            watched = if (it.watched == 1) true else false,
            isLike =  if (it.isLike == 1) true else false,
            url_trailer = it.url_trailer,
            note = it.note
        )
    } as MutableList


}

fun convertMovieToEntity(
    docs: Docs,
    current: Boolean,
    watched: Boolean,
    isLike: Boolean
): HistoryEntity {

    return HistoryEntity(
        0, docs.alternativeName ?: "",
        docs.description ?: "",
        docs.id ?: 0,
        docs.movieLength  ?: "",
        docs.name  ?: "",
        docs.rating?.kp ?: 0.0,
        docs.rating?.russianFilmCritics  ?: 0,
        docs.poster?.url ?: "",
        docs.shortDescription ?: "",
        docs.type ?: "",
        docs.year  ?: 0,
        if (current) 1 else 0,
        if (watched) 1 else 0,
        if (isLike) 1 else 0,
        docs.url_trailer  ?: "",
        docs.note   ?: ""
    )
}
