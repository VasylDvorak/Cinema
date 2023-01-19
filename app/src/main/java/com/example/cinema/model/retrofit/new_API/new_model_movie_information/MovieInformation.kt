package com.example.cinema.model.retrofit.new_API.new_model_movie_information

data class MovieInformation(
    var films: MutableList<Film>? = mutableListOf(),
    var keyword: String ?= "",
    var pagesCount: Int? = 0,
    var searchFilmsCountResult: Int? = 0
)