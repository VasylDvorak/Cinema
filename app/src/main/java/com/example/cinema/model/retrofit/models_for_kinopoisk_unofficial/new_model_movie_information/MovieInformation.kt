package com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_movie_information

data class MovieInformation(
    var films: MutableList<Film> = mutableListOf(),
    var keyword: String = "",
    var pagesCount: Int = 0,
    var searchFilmsCountResult: Int = 0
)