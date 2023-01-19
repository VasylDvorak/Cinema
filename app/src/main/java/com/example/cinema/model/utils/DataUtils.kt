package com.example.cinema.model.utils

import com.example.cinema.model.best_movie_model.MovieDTOBest
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.serch_name_movie_model.Poster
import com.example.cinema.model.serch_name_movie_model.Rating
import com.example.cinema.model.room_data_base.HistoryEntity


fun convertBestToMovieDTO(input: MovieDTOBest): MovieDTO{
    var movieDTO = MovieDTO()
    for (docss in input.docs) {
        var docs_model_studio = Docs()

        docs_model_studio.apply {
            id = docss.id ?: 0
            movieLength = docss.movieLength ?: 0
            description = docss.description ?: ""
            enName = docss.enName ?: ""
            alternativeName = docss.alternativeName  ?: ""
            name = docss.name  ?: ""
            poster.url = docss.posterBest.url  ?: ""
            rating.kp = docss.ratingBest.kp  ?: 0.0
            rating.filmCritics = docss.ratingBest.filmCritics   ?: 0.0
            rating.russianFilmCritics = docss.ratingBest.russianFilmCritics   ?: 0
            year = docss.year   ?: 0
            type = docss.type   ?: ""
            rating.await = docss.ratingBest.await.toInt()   ?: 0
            alternativeName = docss.alternativeName ?: ""
            movieDTO.docs.add(this)
        }
    }
    return movieDTO

}

fun convertHistoryEntityToMovie(entityList: MutableList<HistoryEntity>): MutableList<Docs> {
    return entityList.map {

        Docs(
            alternativeName = it.alternativeName,
            description = it.description,
            id = it.id_server,
            movieLength = it.movieLength,
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
        docs.movieLength  ?: 0,
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
