package com.example.cinema.model.retrofit.new_API.new_model_the_best

data class Item(
    val countries: MutableList<Country> ?=  mutableListOf(),
    val genres: MutableList<Genre> ?= mutableListOf(),
    val imdbId: String? ="",
    val kinopoiskId: Int? =0,
    val nameEn: String? = "",
    val nameOriginal: String? ="",
    val nameRu: String? ="",
    val posterUrl: String? = "",
    val posterUrlPreview: String? ="",
    val ratingImdb: Double? = 0.0,
    val ratingKinopoisk: Double? = 0.0,
    val type: String ="",
    val year: Int = 0
)